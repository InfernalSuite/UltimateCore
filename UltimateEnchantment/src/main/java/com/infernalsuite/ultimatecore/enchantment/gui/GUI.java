package com.infernalsuite.ultimatecore.enchantment.gui;

import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GUI {

    public int scheduler;
    private final Inventory inventory;
    private final boolean decoration;

    @SuppressWarnings("deprecation")
    public GUI(int size, String name, boolean decoration) {
        this.inventory = Bukkit.createInventory(null, size, Utils.color(name));
        this.decoration = decoration;
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EnchantmentsPlugin.getInstance(), this::addContent, 0, 2);
    }

    public void addContent() {
        if (inventory.getViewers().isEmpty()) return;
        if(decoration) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                    setItem(i, InventoryUtils.makeItemHidden(EnchantmentsPlugin.getInstance().getInventories().background));
                }
            }
        }else {
            for (int i = 0; i < inventory.getSize(); i++) {
                if(i != 19) {
                    if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR))
                        setItem(i, InventoryUtils.makeItemHidden(EnchantmentsPlugin.getInstance().getInventories().background));
                }
            }
        }
    }

    public void setItem(int i, ItemStack itemStack) {
        if (inventory.getItem(i) == null || !inventory.getItem(i).isSimilar(itemStack))
            inventory.setItem(i, itemStack);
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
