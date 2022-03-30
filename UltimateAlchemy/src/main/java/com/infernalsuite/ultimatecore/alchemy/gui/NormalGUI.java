package com.infernalsuite.ultimatecore.alchemy.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface NormalGUI extends InventoryHolder {
    void onInventoryClick(InventoryClickEvent event);
}