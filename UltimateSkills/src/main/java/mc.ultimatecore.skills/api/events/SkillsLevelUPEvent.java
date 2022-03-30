package mc.ultimatecore.skills.api.events;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.skills.objects.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SkillsLevelUPEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final SkillType skill;

    @Getter
    private final int level;

    @Getter
    @Setter
    private boolean cancelled;
    /**
     * Called when player level up their skills.
     *
     * @param player Player
     * @param skill SkillType skills where player won xp
     * @param level new level after level up
     * */

    public SkillsLevelUPEvent(Player player, SkillType skill, int level) {
        super(player);
        this.skill = skill;
        this.level = level;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
