package mc.ultimatecore.alchemy.gui;

import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.utils.InventoryUtils;
import mc.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public abstract class GUI {

    public int scheduler;
    private Inventory inventory;
    private Set<Integer> decoration;

    public GUI() {
    }

    @SuppressWarnings("deprecation")
    public GUI(int size, String name) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        this.decoration = new HashSet<>();
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HyperAlchemy.getInstance(), this::addContent, 0, 2);
    }

    @SuppressWarnings("deprecation")
    public GUI(int size, String name, Set<Integer> decoration) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        this.decoration = decoration;
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HyperAlchemy.getInstance(), this::addContent, 0, 2);
    }

    public void addContent() {
        if (inventory.getViewers().isEmpty()) return;
            for (Integer i : decoration) {
                if(i == 23) continue;
                if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                    setItem(i, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().background));
                }
            }
    }

    public void setItem(int i, ItemStack itemStack) {
        if (getInventory().getItem(i) == null || !getInventory().getItem(i).isSimilar(itemStack)) {
            getInventory().setItem(i, itemStack);
        }
    }

    public void setItem(int i, ItemStack itemStack, int quantity) {
        if (getInventory().getItem(i) == null || !getInventory().getItem(i).isSimilar(itemStack)) {
            itemStack.setAmount(quantity);
            getInventory().setItem(i, itemStack);
        }
    }

    public abstract void onInventoryClick(InventoryClickEvent e);

    public Inventory getInventory() {
        return inventory;
    }

}
