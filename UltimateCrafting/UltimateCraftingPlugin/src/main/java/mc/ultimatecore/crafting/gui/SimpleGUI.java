package mc.ultimatecore.crafting.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleGUI implements InventoryHolder {
    public abstract void onInventoryClick(InventoryClickEvent e);

    public void openInventory(Player player){}

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
