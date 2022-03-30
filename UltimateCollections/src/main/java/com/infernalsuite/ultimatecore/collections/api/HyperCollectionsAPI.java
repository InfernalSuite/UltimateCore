package com.infernalsuite.ultimatecore.collections.api;

import com.infernalsuite.ultimatecore.collections.objects.Collection;
import org.bukkit.OfflinePlayer;

public interface HyperCollectionsAPI {
    
    /**
     * Method to get Player level for the specified collection
     *
     * @param key    String
     * @param player OfflinePlayer
     * @return return collection level
     */
    int getLevel(OfflinePlayer player, String key);
    
    /**
     * Method to get Player XP for the specified collection
     *
     * @param key    String
     * @param player OfflinePlayer
     * @return return collection xp
     */
    double getXP(OfflinePlayer player, String key);
    
    /**
     * Method to set Player level for the specified collection
     *
     * @param key      String
     * @param player   OfflinePlayer
     * @param quantity int
     */
    void setLevel(OfflinePlayer player, String key, int quantity);
    
    /**
     * Method to set Player XP for the specified collection
     *
     * @param key      String
     * @param player   OfflinePlayer
     * @param quantity double
     */
    void setXP(OfflinePlayer player, String key, int quantity);
    
    /**
     * Method to add Levels to specified player collection
     *
     * @param key      String
     * @param player   OfflinePlayer
     * @param quantity int
     * @return return skill level
     */
    void addLevel(OfflinePlayer player, String key, int quantity);
    
    /**
     * Method to add XP to specified player collection
     *
     * @param key      String
     * @param player   OfflinePlayer
     * @param quantity double
     */
    void addXP(OfflinePlayer player, String key, int quantity);
    
    /**
     * Method to get Collection instance from a key
     *
     * @param key String
     * @return return Collection instance
     */
    Collection getCollectionInstance(String key);
}
