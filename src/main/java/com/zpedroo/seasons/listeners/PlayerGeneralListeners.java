package com.zpedroo.seasons.listeners;

import com.google.common.base.Joiner;
import com.zpedroo.seasons.managers.DataManager;
import com.zpedroo.seasons.managers.SeasonManager;
import com.zpedroo.seasons.objects.PlayerData;
import com.zpedroo.seasons.utils.config.Messages;
import com.zpedroo.seasons.utils.config.Settings;
import de.tr7zw.nbtapi.NBTItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerGeneralListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (event.getItem() == null || event.getItem().getType().equals(Material.AIR)) return;

        ItemStack item = event.getItem().clone();
        NBTItem nbt = new NBTItem(item);
        if (!nbt.hasKey("SeasonPointsAmount")) return;

        event.setCancelled(true);

        double pointsAmount = nbt.getDouble("SeasonPointsAmount");
        if (pointsAmount <= 0) return;

        String ownerName = nbt.getString("SeasonPointsOwner");
        Player player = event.getPlayer();
        if (!StringUtils.equals(ownerName, player.getName())) {
            player.sendMessage(StringUtils.replaceEach(Messages.ONLY_OWNER, new String[]{
                    "{owner}"
            }, new String[]{
                    ownerName
            }));
            return;
        }

        PlayerData data = DataManager.getInstance().getPlayerData(player);
        if (data == null) return;

        item.setAmount(1);
        player.getInventory().removeItem(item);

        data.addPoints(pointsAmount);

        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 0.5f, 10f);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        DataManager.getInstance().savePlayerData(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent event) {
        if (!SeasonManager.getInstance().isFinished()) return;

        Player player = event.getPlayer();
        if (player.hasPermission(Settings.ADMIN_PERMISSION)) return;

        event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Joiner.on("\n").join(Messages.FINISHED_SEASON_KICK));
    }
}