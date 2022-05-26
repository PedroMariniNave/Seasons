package com.zpedroo.seasons.tasks;

import com.google.common.base.Joiner;
import com.zpedroo.seasons.managers.SeasonManager;
import com.zpedroo.seasons.utils.config.Messages;
import com.zpedroo.seasons.utils.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckTask extends BukkitRunnable {

    public CheckTask(Plugin plugin) {
        this.runTaskTimer(plugin, 0L, 20L * 60L);
    }

    @Override
    public void run() {
        if (!SeasonManager.getInstance().isFinished()) return;

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission(Settings.ADMIN_PERMISSION)) return;

            player.kickPlayer(Joiner.on("\n").join(Messages.FINISHED_SEASON_KICK));
        });
    }
}