package com.infernalsuite.ultimatecore.alchemy.gui;


import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.enums.BrewingSlots;
import com.infernalsuite.ultimatecore.alchemy.managers.AlchemyGUIManager;
import com.infernalsuite.ultimatecore.alchemy.utils.InventoryUtils;
import org.bukkit.Location;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BrewingGUI extends GUI implements Listener {

    private final AlchemyGUIManager alchemyGUIManager;

    public BrewingGUI(Location location) {
        super(HyperAlchemy.getInstance().getInventories().mainMenuSize, HyperAlchemy.getInstance().getInventories().mainMenuTitle, HyperAlchemy.getInstance().getInventories().decorationSlots);
        alchemyGUIManager = new AlchemyGUIManager(getInventory(), location);
        alchemyGUIManager.init(null);
        HyperAlchemy.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        setItem(HyperAlchemy.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().closeButton));
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        Inventory inventory = e.getInventory();
        if(!inventory.equals(getInventory())) return;
        BrewingStand brewingStand = alchemyGUIManager.getBrewingStand();
        if(brewingStand == null) return;
        HashMap<Integer, Integer> relationalSlots = HyperAlchemy.getInstance().getInventories().relationSlots;
        for(Integer slot : relationalSlots.keySet()){
            ItemStack itemStack = brewingStand.getInventory().getItem(slot);
            if(itemStack == null) continue;
            inventory.setItem(relationalSlots.get(slot), itemStack);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e){
        Inventory inventory = e.getInventory();
        if(!inventory.equals(getInventory())) return;
        Player player = (Player) e.getWhoClicked();
        alchemyGUIManager.init(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
        Player player = (Player) e.getWhoClicked();
        if(e.getClickedInventory().equals(getInventory())){
            if (e.getSlot() == HyperAlchemy.getInstance().getInventories().closeButton.slot) {
                e.setCancelled(true);
                player.closeInventory();
            }else if (!BrewingSlots.AVAILABLE_SLOTS.getSlots().contains(e.getSlot())) {
                e.setCancelled(true);
            }
        }
        alchemyGUIManager.init(player);
    }

}
