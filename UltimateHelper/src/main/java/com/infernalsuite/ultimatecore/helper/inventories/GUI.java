package com.infernalsuite.ultimatecore.helper.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface GUI extends InventoryHolder {
    
    @Override
    @NotNull
    Inventory getInventory();
    
    void onInventoryClick(InventoryClickEvent e);
}
