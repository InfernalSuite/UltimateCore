package com.infernalsuite.ultimatecore.menu.gui;


import com.infernalsuite.ultimatecore.menu.HyperCore;
import com.infernalsuite.ultimatecore.menu.Item;
import com.infernalsuite.ultimatecore.menu.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.menu.utils.StringUtils;
import com.infernalsuite.ultimatecore.menu.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class MainMenuGUI implements GUI {

    private final HashMap<Integer, String> commandSlots;

    private final UUID uuid;

    public MainMenuGUI(UUID uuid) {
        this.commandSlots = new HashMap<>();
        this.uuid = uuid;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player)e.getWhoClicked();
            if(e.getSlot() == HyperCore.getInstance().getInventories().closeButton.slot){
                player.closeInventory();
            }else{
                if(commandSlots.containsKey(e.getSlot()))
                    player.performCommand(String.valueOf(commandSlots.get(e.getSlot())).replace("%player%", player.getDisplayName()));
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperCore.getInstance().getInventories().mainMenuSize, StringUtils.color(HyperCore.getInstance().getInventories().mainMenuTitle));
        for (int i = 0; i < inventory.getSize(); i++) {
            if (!HyperCore.getInstance().getInventories().mainMenuExcludedSlots.contains(i))
                inventory.setItem(i, InventoryUtils.makeItem(HyperCore.getInstance().getInventories().background, uuid));
        }
        for (Item item : HyperCore.getInstance().getInventories().items) {
            inventory.setItem(item.slot, InventoryUtils.makeItem(item, Utils.getPlaceholders(uuid), uuid));
            this.commandSlots.put(item.slot, item.command);
        }
        inventory.setItem(HyperCore.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperCore.getInstance().getInventories().closeButton, uuid));
        return inventory;
    }
}
