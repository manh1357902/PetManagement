package com.project.petmanagement.utils;

import java.text.DecimalFormat;

public class FormatNumberUtils {
    public static String formatFloat(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(number).replace(",", ".");
    }
}
