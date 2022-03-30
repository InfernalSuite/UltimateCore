package com.infernalsuite.ultimatecore.crafting.nms;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.metadata.FixedMetadataValue;

public interface NMS {
    
    InventoryView getInventoryView(Player p, String name, World world, Location location, FixedMetadataValue metadataValue);
}