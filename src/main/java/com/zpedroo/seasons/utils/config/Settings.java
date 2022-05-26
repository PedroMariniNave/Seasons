package com.zpedroo.seasons.utils.config;

import com.zpedroo.seasons.utils.FileUtils;

import java.util.List;

public class Settings {

    public static final String COMMAND = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.command");

    public static final List<String> ALIASES = FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Settings.aliases");

    public static final long SAVE_INTERVAL = FileUtils.get().getLong(FileUtils.Files.CONFIG, "Settings.save-interval");

    public static final String DATE_FORMAT = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.date-format");

    public static final String FINISH_DATE = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.finish-date");

    public static final String ADMIN_PERMISSION = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.admin-permission");
}