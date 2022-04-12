package mc.ultimatecore.crafting.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface SimpleGUI extends InventoryHolder {

    default void onInventoryDrag(InventoryDragEvent event) {

    }

    default void onInventoryClick(InventoryClickEvent event) {

    }

    default void onInventoryClose(InventoryCloseEvent event) {

    }

    default void openInventory(Player player) {

    }

}
