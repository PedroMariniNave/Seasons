package com.zpedroo.seasons.utils.config;

import com.zpedroo.seasons.utils.FileUtils;
import com.zpedroo.seasons.utils.color.Colorize;

import java.util.List;

public class Messages {

    public static final String ONLY_OWNER = Colorize.getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.only-owner"));

    public static final List<String> FINISHED_SEASON_KICK = Colorize.getColored(FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Messages.finished-season-kick"));
}