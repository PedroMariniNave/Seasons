package com.zpedroo.seasons.tasks;

import com.zpedroo.seasons.managers.DataManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.zpedroo.seasons.utils.config.Settings.SAVE_INTERVAL;

public class SaveTask extends BukkitRunnable {

    public SaveTask(Plugin plugin) {
        this.runTaskTimerAsynchronously(plugin, 20L * SAVE_INTERVAL, 20L * SAVE_INTERVAL);
    }

    @Override
    public void run() {
        DataManager.getInstance().saveAllPlayersData();
        DataManager.getInstance().updateTopPoints();
    }
}