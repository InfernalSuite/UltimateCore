package com.infernalsuite.ultimatecore.collections.objects;

import com.infernalsuite.ultimatecore.collections.HyperCollections;

import java.util.HashMap;

public class PlayerCollections {
    
    private final HashMap<String, Integer> playerLevel = new HashMap<>();
    
    private final HashMap<String, Integer> playerXP = new HashMap<>();
    
    private final HashMap<String, Integer> rankPosition = new HashMap<>();
    
    public PlayerCollections() {
        for (Collection collection : (HyperCollections.getInstance().getCollections()).collections.values()) {
            this.playerLevel.put(collection.getKey(), 0);
            this.playerXP.put(collection.getKey(), 0);
            this.rankPosition.put(collection.getKey(), 1);
        }
    }
    
    public void addXP(String collections, Integer quantity) {
        if (this.playerXP.containsKey(collections))
            this.playerXP.put(collections, this.playerXP.get(collections) + quantity);
    }
    
    public void addLevel(String collections, Integer quantity) {
        if (this.playerLevel.containsKey(collections))
            this.playerLevel.put(collections, this.playerLevel.get(collections) + quantity);
    }
    
    public int getLevel(String collections) {
        return playerLevel.getOrDefault(collections, 0);
    }
    
    public void setLevel(String collections, Integer level) {
        if (this.playerLevel.containsKey(collections))
            this.playerLevel.put(collections, level);
    }
    
    public void setXP(String collections, Integer xp) {
        if (this.playerXP.containsKey(collections))
            this.playerXP.put(collections, xp);
    }
    
    public Integer getXP(String collections) {
        return playerXP.getOrDefault(collections, 0);
    }
    
    public int getUnlocked(Category category) {
        int unlocked = 0;
        for (Collection collection : (HyperCollections.getInstance().getCollections()).collections.values())
            if (playerXP.get(collection.getKey()) > 0 && category.equals(collection.getCategory()))
                unlocked++;
        return unlocked;
    }
    
    public int getAllUnlocked() {
        int unlocked = 0;
        for (Integer integer : playerXP.values())
            if (integer > 0)
                unlocked++;
        return unlocked;
    }
    
    public double getCollectionValue(String collection) {
        return getLevel(collection) + getXP(collection);
    }
    
    public int getRankPosition(String collection) {
        if (rankPosition.containsKey(collection))
            return rankPosition.get(collection);
        return -1;
    }
    
    public void setRankPosition(String collection, int value) {
        if (rankPosition.containsKey(collection))
            rankPosition.put(collection, value);
    }
    
}
