package mc.ultimatecore.crafting.listeners;

import mc.ultimatecore.crafting.gui.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

public class InventoryDragListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryDragEvent event) {
        Inventory clickedInventory = event.getInventory();
        if (clickedInventory.getHolder() instanceof SimpleGUI) {
            ((SimpleGUI) clickedInventory.getHolder()).onInventoryDrag(event);
        }
    }
}
