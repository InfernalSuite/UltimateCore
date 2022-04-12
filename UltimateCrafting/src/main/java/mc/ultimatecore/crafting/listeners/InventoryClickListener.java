package mc.ultimatecore.crafting.listeners;

import mc.ultimatecore.crafting.gui.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof SimpleGUI) {
            ((SimpleGUI) clickedInventory.getHolder()).onInventoryClick(event);
        }
    }
}
