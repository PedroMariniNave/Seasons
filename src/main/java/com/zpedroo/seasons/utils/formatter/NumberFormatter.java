package com.zpedroo.seasons.utils.formatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter {

    public static String formatDecimal(double number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.ITALIAN);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        return formatter.format(number);
    }
}