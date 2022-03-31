package mc.ultimatecore.farm.events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

public class HyperBlockBreakEvent extends PlayerEvent implements Listener {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Block block;

    /**
     * Called when player break a block through HyperRegions
     *
     * @param player Player
     * @param block  Block block broken
     */

    public HyperBlockBreakEvent(Player player, Block block) {
        super(player);
        this.block = block;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


}
