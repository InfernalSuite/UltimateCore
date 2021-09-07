package mc.ultimatecore.alchemy.listeners;

import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.gui.AllRecipesGUI;
import mc.ultimatecore.alchemy.gui.NormalGUI;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() != null)
            if (event.getInventory().getHolder() instanceof NormalGUI && event.getInventory().getHolder() instanceof AllRecipesGUI) {
                ((NormalGUI) event.getInventory().getHolder()).onInventoryClick(event);
            }
    }

    @EventHandler
    public void on(InventoryMoveItemEvent e) {
        Inventory inventory = e.getDestination();
        if (!(inventory.getHolder() instanceof Hopper)) return;
        Hopper hopper = (Hopper) inventory.getHolder();
        Location brewingLocation = hopper.getBlock().getRelative(BlockFace.UP).getLocation();
        if (!HyperAlchemy.getInstance().getBrewingStandManager().getBrewingStandItems().containsKey(brewingLocation)) return;
        e.setCancelled(true);
    }
}