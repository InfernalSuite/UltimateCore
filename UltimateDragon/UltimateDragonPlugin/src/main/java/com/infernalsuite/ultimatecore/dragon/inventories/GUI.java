package com.infernalsuite.ultimatecore.dragon.inventories;

import lombok.AllArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

@AllArgsConstructor
public abstract class GUI implements InventoryHolder{
    protected final int page;
    public abstract void onInventoryClick(InventoryClickEvent event);
    public void onInventoryClose(InventoryCloseEvent event){}
}
