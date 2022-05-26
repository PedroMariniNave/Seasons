package com.zpedroo.seasons.managers;

import java.util.Date;

public class SeasonManager {

    private static SeasonManager instance;
    public static SeasonManager getInstance() { return instance; }

    public SeasonManager() {
        instance = this;
    }

    public boolean isFinished() {
        return new Date().after(DataManager.getInstance().getFinishDate());
    }
}