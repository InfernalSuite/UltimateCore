package com.infernalsuite.ultimatecore.skills.api.events;

import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.skills.enums.FortuneType;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class SkillsFortuneEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final SkillType skill;

    @Getter
    private final Collection<ItemStack> drops;

    @Getter @Setter
    private int random;

    @Getter
    private final double percentage;

    @Getter
    private final FortuneType fortuneType;

    @Getter
    private final Location dropLocation;

    @Getter
    @Setter
    private boolean cancelled;

    public SkillsFortuneEvent(@NotNull Player player, SkillType skill, Collection<ItemStack> drops, int random, double percentage, FortuneType fortuneType, Location dropLocation) {
        super(player);
        this.skill = skill;
        this.drops = drops;
        this.random = random;
        this.percentage = percentage;
        this.fortuneType = fortuneType;
        this.dropLocation = dropLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
