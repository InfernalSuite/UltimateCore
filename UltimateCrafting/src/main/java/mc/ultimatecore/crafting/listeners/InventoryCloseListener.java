package mc.ultimatecore.crafting.listeners;

import mc.ultimatecore.crafting.gui.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof SimpleGUI) {
            ((SimpleGUI) inventory.getHolder()).onInventoryClose(event);
        }
    }
}
