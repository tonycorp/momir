package com.magic.momir.providers;

import java.util.Map;

public class Utils {
    public static String mapToString(final Map<String, String> columns){
        final StringBuilder builder = new StringBuilder();
        for (final String column : columns.keySet()) {
            builder.append(String.format("%s %s, ", column, columns.get(column)));
        }
        builder.deleteCharAt(builder.length() - 2);
        return builder.toString();
    }
}
