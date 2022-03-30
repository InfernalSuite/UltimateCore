package com.infernalsuite.ultimatecore.anvil.gui;

import com.infernalsuite.ultimatecore.anvil.HyperAnvil;
import com.infernalsuite.ultimatecore.anvil.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.anvil.utils.StringUtils;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class GUI {
    public int scheduler;

    private final Inventory inventory;

    private Set<Integer> excludedSlots;

    public GUI(String name) {
        this.inventory = Bukkit.createInventory(null, InventoryType.ANVIL, Utils.color(name));
    }

    public GUI(int size, String name) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        this.excludedSlots = new HashSet<>();
        this.scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HyperAnvil.getInstance(), this::addContent, 0L, 2L);
    }

    public GUI(int size, String name, Set<Integer> excludedSlots) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        this.excludedSlots = excludedSlots;
        this.scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HyperAnvil.getInstance(), this::addContent, 0L, 2L);
    }

    public void addContent() {
        if (this.inventory.getViewers().isEmpty())
            return;
        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (!this.excludedSlots.contains(i) && (
                    this.inventory.getItem(i) == null || Objects.requireNonNull(this.inventory.getItem(i)).getType().equals(Material.AIR)))
                setItem(i, InventoryUtils.makeItem((HyperAnvil.getInstance().getInventories()).getBackground()));
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

    public void onInventoryClick(InventoryClickEvent paramInventoryClickEvent){}

    public Inventory getInventory() {
        return this.inventory;
    }
}
