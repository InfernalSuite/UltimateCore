package mc.ultimatecore.crafting.listeners;

import lombok.*;
import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.gui.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.util.*;

@AllArgsConstructor
public class InventoryClickListener implements Listener {

    private final HyperCrafting plugin;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof SimpleGUI) {
            ((SimpleGUI) clickedInventory.getHolder()).onInventoryClick(event);
        } else if (clickedInventory == event.getView().getBottomInventory() && event.getView().getTopInventory().getHolder() instanceof SimpleGUI) {
            ((SimpleGUI) event.getView().getTopInventory().getHolder()).onUpdatePlayerInventory(event);
            if(this.plugin.getPlayerManager().createOrGetUser(event.getWhoClicked().getUniqueId()).getCraftingItems().containsKey(event.getSlot())) {
            }
        }
    }
}
