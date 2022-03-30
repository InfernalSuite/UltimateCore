package mc.ultimatecore.menu.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface GUI extends InventoryHolder{

    void onInventoryClick(InventoryClickEvent event);
}
