package com.infernalsuite.ultimatecore.crafting.listeners;

import com.infernalsuite.ultimatecore.crafting.gui.SimpleGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() != null)
            if (event.getInventory().getHolder() instanceof SimpleGUI)
                ((SimpleGUI) event.getInventory().getHolder()).onInventoryClick(event);
    }
}
