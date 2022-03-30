package com.infernalsuite.ultimatecore.collections.addons;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.objects.PlayerCollections;
import org.bukkit.entity.Player;

public class MVDWPlaceholderAPIManager {
    
    public MVDWPlaceholderAPIManager(HyperCollections plugin) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            return;
        }
        PlaceholderAPI.registerPlaceholder(HyperCollections.getInstance(), "collections_unlocked", e -> {
            Player player = e.getPlayer();
            if (player == null) {
                return "0";
            }
            PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId());
            return playerCollection.getAllUnlocked() + "";
        });
        PlaceholderAPI.registerPlaceholder(HyperCollections.getInstance(), "collections_total", e -> {
            Player player = e.getPlayer();
            if (player == null)
                return "0";
            return HyperCollections.getInstance().getCollections().getCollectionQuantity() + "";
        });
        for (String key : HyperCollections.getInstance().getCollections().collections.keySet()) {
            PlaceholderAPI.registerPlaceholder(HyperCollections.getInstance(), "collections_rank_" + key, e -> {
                Player player = e.getPlayer();
                if (player == null)
                    return "0";
                return HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId()).getRankPosition(key) + "";
            });
            PlaceholderAPI.registerPlaceholder(HyperCollections.getInstance(), "collections_level_" + key, e -> {
                Player player = e.getPlayer();
                if (player == null)
                    return "0";
                return HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId()).getLevel(key) + "";
            });
            PlaceholderAPI.registerPlaceholder(HyperCollections.getInstance(), "collections_xp_" + key, e -> {
                Player player = e.getPlayer();
                if (player == null)
                    return "0";
                return HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId()).getXP(key) + "";
            });
        }
    }
}