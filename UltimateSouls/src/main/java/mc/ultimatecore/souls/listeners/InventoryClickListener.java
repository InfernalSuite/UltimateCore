package mc.ultimatecore.souls.listeners;

import mc.ultimatecore.souls.gui.SimpleGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null && e.getInventory().getHolder() != null && e.getInventory().getHolder() instanceof SimpleGUI) {
            e.setCancelled(true);
            ((SimpleGUI) e.getInventory().getHolder()).onInventoryClick(e);
        }
    }
}
