package mc.ultimatecore.crafting.gui;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class GUI {

    private final HyperCrafting plugin;
    public int scheduler;
    private final Inventory inventory;
    private Set<Integer> decoration;

    public GUI(int size, String name, Set<Integer> decoration, HyperCrafting plugin) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(null, size, Utils.color(name));
        this.decoration = decoration;
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, this::addContent, 0, 2);
    }

    public GUI(String name, HyperCrafting plugin) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(null, InventoryType.DISPENSER, Utils.color(name));
    }

    public void addContent() {
        if (inventory.getViewers().isEmpty()) return;
        for (Integer i : decoration) {
            if (i == 23) continue;
            if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                setItem(i, InventoryUtils.makeItemHidden(this.plugin.getInventories().background));
            }
        }
    }

    public void setItem(int i, ItemStack itemStack) {
        if (getInventory().getItem(i) == null || !getInventory().getItem(i).isSimilar(itemStack)) {
            getInventory().setItem(i, itemStack);
        }
    }

    public abstract void onInventoryClick(InventoryClickEvent e);

    public Inventory getInventory() {
        return inventory;
    }

}
