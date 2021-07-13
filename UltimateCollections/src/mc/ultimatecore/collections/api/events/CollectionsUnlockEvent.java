package mc.ultimatecore.collections.api.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class CollectionsUnlockEvent extends PlayerEvent implements Cancellable {
    
    private static final HandlerList handlers = new HandlerList();
    
    private final String key;
    
    @Getter
    @Setter
    private boolean cancelled;
    
    /**
     * Called when player unlock a collection.
     *
     * @param player Player
     * @param key    Collection key
     */
    
    public CollectionsUnlockEvent(Player player, String key) {
        super(player);
        this.key = key;
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
}