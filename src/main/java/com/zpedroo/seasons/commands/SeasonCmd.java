package com.zpedroo.seasons.commands;

import com.zpedroo.seasons.managers.DataManager;
import com.zpedroo.seasons.utils.menu.Menus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SeasonCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length > 0) {
            switch (args[0].toUpperCase()) {
                case "TOP":
                    if (player != null) Menus.getInstance().openTopPointsMenu(player);
                    return true;
                case "ITEM":
                    if (player != null) break; // only console

                    String targetName = args[1];
                    Player target = Bukkit.getPlayer(targetName);
                    if (target == null) return true;

                    double pointsAmount = 0;
                    try {
                        pointsAmount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException ex) {
                        // ignore
                    }
                    if (pointsAmount <= 0) break;

                    ItemStack item = DataManager.getInstance().getPointsItem(pointsAmount, targetName);
                    if (target.getInventory().firstEmpty() != -1) {
                        target.getInventory().addItem(item);
                    } else {
                        target.getWorld().dropItemNaturally(target.getLocation(), item);
                    }
                    return true;
            }
        }

        if (player != null) {
            Menus.getInstance().openMainMenu(player);
        }
        return false;
    }
}