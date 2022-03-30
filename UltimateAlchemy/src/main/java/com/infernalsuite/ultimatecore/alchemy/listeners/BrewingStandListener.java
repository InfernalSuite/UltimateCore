package com.infernalsuite.ultimatecore.alchemy.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.gui.BrewingGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BrewingStandListener implements Listener {
    @EventHandler
    public void onOpenBrewingStand(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block bl = e.getClickedBlock();
        if(bl == null || bl.getType() != Material.BREWING_STAND) return;
        e.setCancelled(true);
        Location location = bl.getLocation();
        if(!HyperAlchemy.getInstance().getBrewingStandManager().getBrewingStandItems().containsKey(location))
            HyperAlchemy.getInstance().getBrewingStandManager().getBrewingStandItems().put(location, new BrewingGUI(location));
        BrewingGUI gui = HyperAlchemy.getInstance().getBrewingStandManager().getBrewingStandItems().get(location);
        e.getPlayer().openInventory(gui.getInventory());
    }

    @EventHandler
    public void removeBrewingStand(BlockBreakEvent e){
        if(e.isCancelled()) return;
        Block bl = e.getBlock();
        if(!bl.getType().equals(XMaterial.BREWING_STAND.parseMaterial())) return;
        HyperAlchemy.getInstance().getBrewingStandManager().getBrewingStandItems().remove(bl.getLocation());
    }

}
