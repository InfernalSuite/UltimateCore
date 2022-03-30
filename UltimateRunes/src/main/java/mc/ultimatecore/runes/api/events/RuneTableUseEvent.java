package mc.ultimatecore.runes.api.events;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.runes.enums.RuneState;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RuneTableUseEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final RuneState runeState;

    @Getter @Setter
    private boolean isCancelled;

    /**
     * Called when player fuse an items or runes in the rune table.
     *
     * @param player Player
     * @param runeState RuneState current runetable state
     * */
    public RuneTableUseEvent(Player player, RuneState runeState) {
        super(player);
        this.runeState = runeState;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public RuneState getRuneState() {
        return runeState;
    }

}
