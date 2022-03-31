package mc.ultimatecore.reforge.gui;

import mc.ultimatecore.reforge.HyperReforge;
import mc.ultimatecore.reforge.utils.InventoryUtils;
import mc.ultimatecore.reforge.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class GUI {
    public int scheduler;

    private final Inventory inventory;

    private final Set<Integer> excludedSlots;

    public GUI(int size, String name) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        this.excludedSlots = new HashSet<>();
        this.scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HyperReforge.getInstance(), this::addContent, 0L, 2L);
    }

    public GUI(int size, String name, Set<Integer> excludedSlots) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        this.excludedSlots = excludedSlots;
        this.scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HyperReforge.getInstance(), this::addContent, 0L, 2L);
    }

    public void addContent() {
        if (this.inventory.getViewers().isEmpty())
            return;
        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (!this.excludedSlots.contains(i) && (
                    this.inventory.getItem(i) == null || Objects.requireNonNull(this.inventory.getItem(i)).getType().equals(Material.AIR)))
                setItem(i, InventoryUtils.makeItem((HyperReforge.getInstance().getInventories()).getBackground()));
        }
    }

    public void setItem(int i, ItemStack itemStack) {
        if (getInventory().getItem(i) == null || getInventory().getItem(i) != itemStack)
            getInventory().setItem(i, itemStack);
    }

    public void setItem(int i, ItemStack itemStack, int quantity) {
        if (getInventory().getItem(i) == null || getInventory().getItem(i) != itemStack) {
            itemStack.setAmount(quantity);
            getInventory().setItem(i, itemStack);
        }
    }

    public abstract void onInventoryClick(InventoryClickEvent paramInventoryClickEvent);

    public Inventory getInventory() {
        return this.inventory;
    }
}
