package com.infernalsuite.ultimatecore.collections.gui;


import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.Item;
import com.infernalsuite.ultimatecore.collections.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.collections.utils.StringUtils;
import com.infernalsuite.ultimatecore.collections.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor

public class MainMenuGUI implements GUI {
    
    private final UUID uuid;
    
    private final HyperCollections plugin = HyperCollections.getInstance();
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == HyperCollections.getInstance().getInventories().getCloseButton().slot) {
                player.closeInventory();
            } else if (e.getSlot() == HyperCollections.getInstance().getInventories().getMainMenuItem().slot) {
                player.openInventory(new TopGUI(uuid).getInventory());
            } else if (e.getSlot() == HyperCollections.getInstance().getInventories().getMainMenuBack().slot && HyperCollections.getInstance().getInventories().isMainMenuBackEnabled()) {
                player.closeInventory();
                String command = plugin.getInventories().getMainMenuBack().command;
                if (command.contains("%player%"))
                    Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())), 3);
                else
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.performCommand(command), 3);
            } else {
                for (Item item : HyperCollections.getInstance().getInventories().getMainMenu()) {
                    if (item.slot == e.getSlot()) {
                        if (item.command == null) return;
                        player.closeInventory();
                        Bukkit.getServer().dispatchCommand(e.getWhoClicked(), item.command);
                        return;
                    }
                }
            }
        }
    }
    
    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperCollections.getInstance().getInventories().getMainMenuSize(), StringUtils.color(HyperCollections.getInstance().getInventories().getMainMenuTitle()));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getBackground()));
        if (HyperCollections.getInstance().getInventories().isMainMenuBackEnabled())
            inventory.setItem(HyperCollections.getInstance().getInventories().getMainMenuBack().slot, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getMainMenuBack()));
        for (Item item : HyperCollections.getInstance().getInventories().getMainMenu())
            inventory.setItem(item.slot, InventoryUtils.makeItem(item, Utils.getCategoriesPlaceholder(uuid, item.category)));
        inventory.setItem(HyperCollections.getInstance().getInventories().getMainMenuItem().slot, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getMainMenuItem(), Utils.getGlobalPlaceHolders(uuid)));
        inventory.setItem((HyperCollections.getInstance().getInventories()).getCloseButton().slot, InventoryUtils.makeItem((HyperCollections.getInstance().getInventories()).getCloseButton()));
        return inventory;
    }
}
