package com.zpedroo.seasons.utils.formatter;

import com.zpedroo.seasons.utils.FileUtils;

import java.util.concurrent.TimeUnit;

import static com.zpedroo.seasons.utils.color.Colorize.getColored;
import static com.zpedroo.seasons.utils.formatter.TimeFormatter.Translations.*;

public class TimeFormatter {

    public static String formatTimeByMillis(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) - (days * 24);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - (TimeUnit.MILLISECONDS.toHours(millis) * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - (TimeUnit.MILLISECONDS.toMinutes(millis) * 60);

        StringBuilder builder = new StringBuilder();

        if (days > 0) builder.append(days).append(" ").append(days == 1 ? DAY : DAYS).append(" ");
        if (hours > 0) builder.append(hours).append(" ").append(hours == 1 ? HOUR : HOURS).append(" ");
        if (minutes > 0) builder.append(minutes).append(" ").append(minutes == 1 ? MINUTE : MINUTES).append(" ");
        if (seconds > 0) builder.append(seconds).append(" ").append(seconds == 1 ? SECOND : SECONDS);

        String ret = builder.toString();

        return ret.isEmpty() ? NOW : ret;
    }

    static class Translations {

        public static final String SECOND = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.second"));

        public static final String SECONDS = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.seconds"));

        public static final String MINUTE = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.minute"));

        public static final String MINUTES = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.minutes"));

        public static final String HOUR = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.hour"));

        public static final String HOURS = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.hours"));

        public static final String DAY = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.day"));

        public static final String DAYS = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.days"));

        public static final String NOW = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Time-Formatter.now"));
    }
}