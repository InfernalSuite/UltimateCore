package com.infernalsuite.ultimatecore.collections.api;

import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.objects.Collection;
import org.bukkit.OfflinePlayer;

@AllArgsConstructor
public class HyperCollectionsAPIImpl implements HyperCollectionsAPI {
    
    private final HyperCollections plugin;
    
    @Override
    public int getLevel(OfflinePlayer player, String key) {
        return plugin.getCollectionsManager().getLevel(player, key);
    }
    
    @Override
    public double getXP(OfflinePlayer player, String key) {
        return plugin.getCollectionsManager().getXP(player, key);
    }
    
    @Override
    public void setLevel(OfflinePlayer player, String key, int level) {
        plugin.getCollectionsManager().setLevel(player, key, level);
    }
    
    @Override
    public void setXP(OfflinePlayer player, String key, int xp) {
        plugin.getCollectionsManager().setXP(player, key, xp);
    }
    
    @Override
    public void addLevel(OfflinePlayer player, String key, int level) {
        plugin.getCollectionsManager().addLevel(player, key, level);
    }
    
    @Override
    public void addXP(OfflinePlayer player, String key, int xp) {
        plugin.getCollectionsManager().addXP(player, key, xp);
    }
    
    @Override
    public Collection getCollectionInstance(String key) {
        return plugin.getCollections().getCollection(key);
    }
}
