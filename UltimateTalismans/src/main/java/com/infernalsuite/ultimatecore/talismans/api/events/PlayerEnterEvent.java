package com.infernalsuite.ultimatecore.talismans.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerEnterEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerEnterEvent(Player who) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
