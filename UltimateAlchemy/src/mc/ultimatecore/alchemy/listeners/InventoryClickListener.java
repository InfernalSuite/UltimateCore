package mc.ultimatecore.alchemy.listeners;

import mc.ultimatecore.alchemy.gui.AllRecipesGUI;
import mc.ultimatecore.alchemy.gui.NormalGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() != null)
            if (event.getInventory().getHolder() instanceof NormalGUI && event.getInventory().getHolder() instanceof AllRecipesGUI){
                ((NormalGUI) event.getInventory().getHolder()).onInventoryClick(event);
            }
    }
}
