package com.infernalsuite.ultimatecore.skills.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PAPIUtils {
    public static String translatePAPIPlaceholders(Player player, String str){
        return PlaceholderAPI.setPlaceholders(player, str);
    }
}
