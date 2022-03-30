package com.infernalsuite.ultimatecore.skills.api.events;

import com.infernalsuite.ultimatecore.skills.managers.IndicatorType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class SkillsDamageEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Entity victim;

    @Getter
    private final IndicatorType indicatorType;

    @Getter @Setter
    private boolean cancelled;

    @Getter
    private final double damage;

    public SkillsDamageEvent(@NotNull Player who, Entity victim, IndicatorType indicatorType, double damage) {
        super(who);
        this.victim = victim;
        this.indicatorType = indicatorType;
        this.damage = damage;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
