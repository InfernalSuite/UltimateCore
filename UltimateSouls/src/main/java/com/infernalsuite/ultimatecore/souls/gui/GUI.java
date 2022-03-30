package com.infernalsuite.ultimatecore.souls.gui;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.souls.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GUI {
    
    public int scheduler;
    private Inventory inventory;
    
    public GUI() {
    }
    
    @SuppressWarnings("deprecation")
    public GUI(int size, String name) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HyperSouls.getInstance(), this::addContent, 0, 2);
    }
    
    public void addContent() {
        if (inventory.getViewers().isEmpty()) return;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                setItem(i, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().background));
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
