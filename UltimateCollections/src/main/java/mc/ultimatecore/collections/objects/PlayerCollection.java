package mc.ultimatecore.collections.objects;

import mc.ultimatecore.collections.HyperCollections;
import org.bukkit.*;

import java.util.*;

public class PlayerCollection {

    private final UUID uuid;
    private final HashMap<String, Integer> playerLevel = new HashMap<>();
    private final HashMap<String, Integer> playerXP = new HashMap<>();
    private final HashMap<String, Integer> rankPosition = new HashMap<>();

    public PlayerCollection(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getLevel(String collection) {
        return this.playerLevel.computeIfAbsent(collection, x -> 0);
    }

    public int getXP(String collection) {
        return this.playerXP.computeIfAbsent(collection, x -> 0);
    }

    public void addXP(String collection, int quantity) {
        this.playerXP.put(collection, this.getXP(collection) + quantity);
        HyperCollections.getInstance().getCollectionsManager().checkLevelUp(Bukkit.getOfflinePlayer(this.uuid), collection);
    }

    public void addLevel(String collection, int quantity) {
        this.playerLevel.put(collection, this.getLevel(collection) + quantity);
        HyperCollections.getInstance().getCollectionsManager().checkLevelUp(Bukkit.getOfflinePlayer(this.uuid), collection);
    }

    public void setXP(String collection, int xp) {
        this.playerXP.put(collection, xp);
        HyperCollections.getInstance().getCollectionsManager().checkLevelUp(Bukkit.getOfflinePlayer(this.uuid), collection);
    }

    public void setLevel(String collection, int level) {
        this.playerLevel.put(collection, level);
        HyperCollections.getInstance().getCollectionsManager().checkLevelUp(Bukkit.getOfflinePlayer(this.uuid), collection);
    }

    public int getCollectionValue(String collection) {
        return this.getLevel(collection) + this.getXP(collection);
    }

    public int getUnlocked(Category category) {
        int unlocked = 0;
        for (Collection collection : HyperCollections.getInstance().getCollections().collections.values()) {
            if(this.getXP(collection.getKey()) > 0 && category.equals(collection.getCategory())) {
                unlocked++;
            }
        }
        return unlocked;
    }

    public int getAllUnlocked() {
        int unlocked = 0;
        for (Integer value : this.playerXP.values()) {
            if(value > 0) {
                unlocked++;
            }
        }
        return unlocked;
    }

    public int getRankPosition(String collection) {
        return this.rankPosition.computeIfAbsent(collection, x -> 1);
    }

    public void setRankPosition(String collection, int value) {
        if(this.rankPosition.containsKey(collection)) {
            this.rankPosition.put(collection, value);
        }else {
            this.rankPosition.putIfAbsent(collection, value);
        }
    }
    
}
