package com.zpedroo.seasons.utils.menu;

import com.zpedroo.seasons.managers.DataManager;
import com.zpedroo.seasons.objects.PlayerData;
import com.zpedroo.seasons.utils.FileUtils;
import com.zpedroo.seasons.utils.builder.InventoryBuilder;
import com.zpedroo.seasons.utils.builder.InventoryUtils;
import com.zpedroo.seasons.utils.builder.ItemBuilder;
import com.zpedroo.seasons.utils.color.Colorize;
import com.zpedroo.seasons.utils.formatter.NumberFormatter;
import com.zpedroo.seasons.utils.formatter.TimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menus extends InventoryUtils {

    private static Menus instance;
    public static Menus getInstance() { return instance; }

    public Menus() {
        instance = this;
    }

    public void openMainMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.MAIN;

        String title = Colorize.getColored(FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        InventoryBuilder inventory = new InventoryBuilder(title, size);
        PlayerData data = DataManager.getInstance().getPlayerData(player);

        for (String items : FileUtils.get().getSection(file, "Inventory.items")) {
            String action = FileUtils.get().getString(file, "Inventory.items." + items + ".action");
            ItemStack item = ItemBuilder.build(
                    FileUtils.get().getFile(file).getFileConfiguration(), "Inventory.items." + items, new String[]{
                            "{player}",
                            "{points}",
                            "{remaining_time}"
                    }, new String[]{
                            player.getName(),
                            NumberFormatter.formatDecimal(data.getPointsAmount()),
                            TimeFormatter.formatTimeByMillis(DataManager.getInstance().getFinishDate().getTime() - System.currentTimeMillis())
                    }
            ).build();
            int slot = FileUtils.get().getInt(file, "Inventory.items." + items + ".slot");

            inventory.addItem(item, slot, () -> {
                switch (action.toUpperCase()) {
                    case "TOP":
                        openTopPointsMenu(player);
                        break;
                }
            }, InventoryUtils.ActionType.ALL_CLICKS);
        }

        inventory.open(player);
    }

    public void openTopPointsMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.TOP;

        String title = Colorize.getColored(FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        InventoryBuilder inventory = new InventoryBuilder(title, size);

        int pos = 0;
        String[] slots = FileUtils.get().getString(file, "Inventory.slots").replace(" ", "").split(",");

        for (PlayerData data : DataManager.getInstance().getCache().getTopPoints()) {
            if (++pos > slots.length) break;

            ItemStack item = ItemBuilder.build(FileUtils.get().getFile(file).getFileConfiguration(), "Item", new String[]{
                    "{player}",
                    "{points}",
                    "{pos}"
            }, new String[]{
                    Bukkit.getOfflinePlayer(data.getUniqueId()).getName(),
                    NumberFormatter.formatDecimal(data.getPointsAmount()),
                    String.valueOf(pos)
            }).build();

            int slot = Integer.parseInt(slots[pos-1]);

            inventory.addItem(item, slot);
        }

        inventory.open(player);
    }
}