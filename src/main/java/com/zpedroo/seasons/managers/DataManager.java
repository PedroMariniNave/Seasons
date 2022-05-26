package com.zpedroo.seasons.managers;

import com.zpedroo.seasons.managers.cache.DataCache;
import com.zpedroo.seasons.mysql.DBConnection;
import com.zpedroo.seasons.objects.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.HashSet;

public class DataManager {

    private static DataManager instance;
    public static DataManager getInstance() { return instance; }

    private final DataCache dataCache = new DataCache();

    public DataManager() {
        instance = this;
    }

    public ItemStack getPointsItem(double pointsAmount, String ownerName) {
        return dataCache.getPointsItem(pointsAmount, ownerName);
    }

    public Date getFinishDate() {
        return dataCache.getFinishDate();
    }

    public PlayerData getPlayerData(Player player) {
        PlayerData data = dataCache.getPlayersData().get(player);
        if (data == null) {
            data = DBConnection.getInstance().getDBManager().getPlayerData(player);
            dataCache.getPlayersData().put(player, data);
        }

        return data;
    }

    public void savePlayerData(Player player) {
        PlayerData data = dataCache.getPlayersData().remove(player);
        if (data == null || !data.isQueueUpdate()) return;

        DBConnection.getInstance().getDBManager().savePlayerData(data);
        data.setUpdate(false);
    }

    public void saveAllPlayersData() {
        new HashSet<>(dataCache.getPlayersData().keySet()).forEach(this::savePlayerData);
    }

    public void updateTopPoints() {
        dataCache.updateTopPoints();
    }

    public DataCache getCache() {
        return dataCache;
    }
}