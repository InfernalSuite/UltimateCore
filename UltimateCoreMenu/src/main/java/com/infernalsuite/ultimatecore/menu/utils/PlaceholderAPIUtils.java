package com.infernalsuite.ultimatecore.menu.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderAPIUtils {
    public static List<String> processMultiplePlaceholders(List<String> lines, List<Placeholder> placeholders, Player player) {
        return lines.stream().map(s -> processMultiplePlaceholders(s, placeholders, player)).collect(Collectors.toList());
    }

    public static String processMultiplePlaceholders(String line, List<Placeholder> placeholders, Player player) {
        String processedLine = line;
        for (Placeholder placeholder : placeholders)
            processedLine = PlaceholderAPI.setPlaceholders(player, placeholder.process(processedLine));
        return StringUtils.color(processedLine);
    }
}
