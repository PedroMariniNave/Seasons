package com.zpedroo.seasons.objects;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private double pointsAmount;
    private boolean update = false;

    public PlayerData(UUID uuid, double pointsAmount) {
        this.uuid = uuid;
        this.pointsAmount = pointsAmount;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public double getPointsAmount() {
        return pointsAmount;
    }

    public boolean isQueueUpdate() {
        return update;
    }

    public void addPoints(double points) {
        this.setPointsAmount(this.pointsAmount + points);
    }

    public void removePoints(double points) {
        this.setPointsAmount(this.pointsAmount - points);
    }

    public void setPointsAmount(double pointsAmount) {
        this.pointsAmount = pointsAmount;
        this.update = true;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}