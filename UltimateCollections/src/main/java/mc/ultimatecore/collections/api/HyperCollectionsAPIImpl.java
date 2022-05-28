package mc.ultimatecore.collections.api;

import lombok.AllArgsConstructor;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.objects.Collection;
import org.bukkit.OfflinePlayer;

@AllArgsConstructor
public class HyperCollectionsAPIImpl implements HyperCollectionsAPI {
    
    private final HyperCollections plugin;
    
    @Override
    public int getLevel(OfflinePlayer player, String key) {
        return plugin.getCollectionsManager().createOrGetUser(player.getUniqueId()).getLevel(key);
    }
    
    @Override
    public double getXP(OfflinePlayer player, String key) {
        return plugin.getCollectionsManager().createOrGetUser(player.getUniqueId()).getXP(key);
    }
    
    @Override
    public void setLevel(OfflinePlayer player, String key, int level) {
        plugin.getCollectionsManager().createOrGetUser(player.getUniqueId()).setLevel(key, level);
    }
    
    @Override
    public void setXP(OfflinePlayer player, String key, int xp) {
        plugin.getCollectionsManager().createOrGetUser(player.getUniqueId()).setXP(key, xp);
    }
    
    @Override
    public void addLevel(OfflinePlayer player, String key, int level) {
        plugin.getCollectionsManager().createOrGetUser(player.getUniqueId()).addLevel(key, level);
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
