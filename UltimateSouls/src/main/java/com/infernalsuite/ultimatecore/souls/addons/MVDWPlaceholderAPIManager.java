package com.infernalsuite.ultimatecore.souls.addons;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.infernalsuite.ultimatecore.souls.HyperSouls;

public class MVDWPlaceholderAPIManager {
    
    public MVDWPlaceholderAPIManager(HyperSouls plugin) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            return;
        }
        PlaceholderAPI.registerPlaceholder(plugin, "souls_collected", e -> plugin.getDatabaseManager().getSoulsData(e.getPlayer().getUniqueId()).getAmount() + "");
        PlaceholderAPI.registerPlaceholder(plugin, "souls_total", e -> plugin.getSouls().souls.size() + "");
    }
}