package com.infernalsuite.ultimatecore.collections.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class CollectionsLevelUPEvent extends PlayerEvent {
    
    private static final HandlerList handlers = new HandlerList();
    
    private final String key;
    
    private final int level;
    
    /**
     * Called when player level up in some collection.
     *
     * @param player Player
     * @param key    collection key
     * @param level  int
     */
    
    public CollectionsLevelUPEvent(Player player, String key, int level) {
        super(player);
        this.key = key;
        this.level = level;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public final HandlerList getHandlers() {
        return handlers;
    }
    
    public String getKey() {
        return key;
    }
    
    public int getLevel() {
        return level;
    }
}