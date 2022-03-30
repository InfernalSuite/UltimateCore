package com.infernalsuite.ultimatecore.skills.api.events;

import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SkillsXPGainEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final SkillType skill;

    @Getter
    private final Double quantity;

    @Getter @Setter
    private boolean cancelled;

    /**
     * Called when player gain skills xp.
     *
     * @param player Player
     * @param skill SkillType skills where player won xp
     * @param quantity amount of xp won
     * */

    public SkillsXPGainEvent(Player player, SkillType skill, Double quantity) {
        super(player);
        this.skill = skill;
        this.quantity = quantity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}