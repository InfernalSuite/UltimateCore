package com.infernalsuite.ultimatecore.trades.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface GUI extends InventoryHolder {

    boolean saveOnClose();

    void onInventoryClick(InventoryClickEvent event);
}
