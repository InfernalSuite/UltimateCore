package com.infernalsuite.ultimatecore.dragon.listeners;

import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private final HyperDragons plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getDatabaseManager().addIntoTable(e.getPlayer());
        plugin.getDatabaseManager().loadPlayerData(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getDatabaseManager().savePlayerData(e.getPlayer(), true, true);
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        plugin.getDatabaseManager().savePlayerData(e.getPlayer(), true, true);
    }

}
