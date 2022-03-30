package mc.ultimatecore.souls.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface SimpleGUI extends InventoryHolder {
    
    void onInventoryClick(InventoryClickEvent e);
}
