package com.zpedroo.seasons.managers.cache;

import com.zpedroo.seasons.mysql.DBConnection;
import com.zpedroo.seasons.objects.PlayerData;
import com.zpedroo.seasons.utils.FileUtils;
import com.zpedroo.seasons.utils.builder.ItemBuilder;
import com.zpedroo.seasons.utils.config.Settings;
import com.zpedroo.seasons.utils.formatter.NumberFormatter;
import de.tr7zw.nbtapi.NBTItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataCache {

    private final Map<Player, PlayerData> playersData = new HashMap<>(64);
    private final ItemStack pointsItem = getPointsItemFromFile();
    private final Date finishDate = getFinishDateFromFile();
    private List<PlayerData> topPoints = DBConnection.getInstance().getDBManager().getTopPoints();

    public Map<Player, PlayerData> getPlayersData() {
        return playersData;
    }

    public List<PlayerData> getTopPoints() {
        return topPoints;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void updateTopPoints() {
        this.topPoints = DBConnection.getInstance().getDBManager().getTopPoints();
    }

    public ItemStack getPointsItem(double pointsAmount, String ownerName) {
        NBTItem nbt = new NBTItem(pointsItem.clone());
        nbt.setDouble("SeasonPointsAmount", pointsAmount);
        nbt.setString("SeasonPointsOwner", ownerName);

        ItemStack item = nbt.getItem();
        if (item.getItemMeta() != null) {
            String displayName = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : null;
            List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : null;
            ItemMeta meta = item.getItemMeta();

            if (displayName != null) meta.setDisplayName(StringUtils.replaceEach(displayName, new String[] {
                    "{amount}"
            }, new String[] {
                    NumberFormatter.formatDecimal(pointsAmount)
            }));

            if (lore != null) {
                List<String> newLore = new ArrayList<>(lore.size());

                for (String str : lore) {
                    newLore.add(StringUtils.replaceEach(str, new String[] {
                            "{amount}"
                    }, new String[] {
                            NumberFormatter.formatDecimal(pointsAmount)
                    }));
                }

                meta.setLore(newLore);
            }

            item.setItemMeta(meta);
        }

        return item;
    }

    private ItemStack getPointsItemFromFile() {
        return ItemBuilder.build(FileUtils.get().getFile(FileUtils.Files.CONFIG).getFileConfiguration(), "Points-Item").build();
    }

    private Date getFinishDateFromFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Settings.DATE_FORMAT);
        Date finishDate = null;

        try {
            finishDate = dateFormat.parse(Settings.FINISH_DATE);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return finishDate;
    }
}