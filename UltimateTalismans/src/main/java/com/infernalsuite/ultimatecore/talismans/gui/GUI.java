package com.infernalsuite.ultimatecore.talismans.gui;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.utils.StringUtils;
import lombok.Getter;
import com.infernalsuite.ultimatecore.talismans.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GUI {
    @Getter
    private final Inventory inventory;
    protected final HyperTalismans plugin;

    public GUI(int size, String name, HyperTalismans plugin) {
        this.inventory = Bukkit.createInventory(null, size, StringUtils.color(name));
        this.plugin = plugin;
        addItems();
    }
    public void setItem(int i, ItemStack itemStack) {
        if (getInventory().getItem(i) == null || !getInventory().getItem(i).isSimilar(itemStack))
            getInventory().setItem(i, itemStack);
    }

    protected abstract void onInventoryClick(InventoryClickEvent e);

    protected abstract void onInventoryClose(InventoryCloseEvent e);

    protected void addItems(){
        plugin.getInventories().decorationSlots.forEach(slot ->  setItem(slot, InventoryUtils.makeItem(plugin.getInventories().background)));
        setItem(plugin.getInventories().closeButton.slot, InventoryUtils.makeItem(plugin.getInventories().closeButton));

    }
}
