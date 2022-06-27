package mc.ultimatecore.crafting.listeners;

import lombok.*;
import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.gui.*;
import mc.ultimatecore.crafting.gui.crafting.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

@AllArgsConstructor
public class InventoryCloseListener implements Listener {

    private final HyperCrafting plugin;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof CraftingGUI) {
            Inventory topInventory = event.getView().getTopInventory();
            for (Integer craftingSlot : this.plugin.getCraftingSlots()) {
                if(topInventory.getItem(craftingSlot) != null) {
                    event.getPlayer().getInventory().addItem(topInventory.getItem(craftingSlot));
                    topInventory.setItem(craftingSlot, null);
                }
            }
            ((SimpleGUI) inventory.getHolder()).onInventoryClose(event);
        }
    }
}
