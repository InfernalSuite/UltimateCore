package com.infernalsuite.ultimatecore.souls.listeners;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerJoinLeaveListener implements Listener {
    
    private final HyperSouls plugin;
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try {
            plugin.getDatabaseManager().addIntoTable(event.getPlayer());
            plugin.getDatabaseManager().loadPlayerData(event.getPlayer());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        try {
            plugin.getDatabaseManager().savePlayerData(event.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @EventHandler
    public void onKick(PlayerKickEvent event) {
        try {
            plugin.getDatabaseManager().savePlayerData(event.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
