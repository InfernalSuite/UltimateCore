package mc.ultimatecore.trades.listeners;

import mc.ultimatecore.trades.HyperTrades;
import mc.ultimatecore.trades.gui.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() != null) {
            if (event.getInventory().getHolder() instanceof GUI) {
                event.setCancelled(true);
                ((GUI) event.getInventory().getHolder()).onInventoryClick(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof GUI) {
            GUI gui = (GUI) event.getInventory().getHolder();
            if(gui.saveOnClose())
                HyperTrades.getInstance().getTradesManager().save();
        }
    }
}
