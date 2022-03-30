package com.infernalsuite.ultimatecore.collections.listeners;

import com.infernalsuite.ultimatecore.collections.HyperCollections;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveListener implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try {
            HyperCollections.getInstance().getCollectionsManager().addIntoTable(event.getPlayer());
            HyperCollections.getInstance().getCollectionsManager().loadPlayerData(event.getPlayer());
        } catch (Exception e) {
            HyperCollections.getInstance().sendErrorMessage(e);
        }
    }
    
    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        try {
            HyperCollections.getInstance().getCollectionsManager().savePlayerData(event.getPlayer(), true, true);
        } catch (Exception e) {
            HyperCollections.getInstance().sendErrorMessage(e);
        }
    }
}
