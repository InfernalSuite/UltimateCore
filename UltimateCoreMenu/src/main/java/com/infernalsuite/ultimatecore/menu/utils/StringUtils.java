package com.infernalsuite.ultimatecore.menu.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;


public class StringUtils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> strings) {
        return strings.stream().map(StringUtils::color).collect(Collectors.toList());
    }

    public static List<String> processMultiplePlaceholders(List<String> lines, List<Placeholder> placeholders) {
        return lines.stream().map(s -> processMultiplePlaceholders(s, placeholders)).collect(Collectors.toList());
    }

    public static String processMultiplePlaceholders(String line, List<Placeholder> placeholders) {
        String processedLine = line;
        for (Placeholder placeholder : placeholders) {
            processedLine = placeholder.process(processedLine);
        }
        return color(processedLine);
    }
}