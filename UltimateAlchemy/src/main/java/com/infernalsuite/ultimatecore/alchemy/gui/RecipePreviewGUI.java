package com.infernalsuite.ultimatecore.alchemy.gui;


import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.objects.AlchemyRecipe;
import com.infernalsuite.ultimatecore.alchemy.utils.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashSet;

public class RecipePreviewGUI extends GUI implements Listener {

    private final AlchemyRecipe alchemyRecipe;

    public RecipePreviewGUI(AlchemyRecipe craftingRecipe) {
        super(HyperAlchemy.getInstance().getInventories().recipePreviewSize, HyperAlchemy.getInstance().getInventories().recipePreviewTitle, new HashSet<>(HyperAlchemy.getInstance().getInventories().recipePreviewDecoration));
        this.alchemyRecipe = craftingRecipe;
        HyperAlchemy.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if(alchemyRecipe == null) return;
        setItem(HyperAlchemy.getInstance().getInventories().mainIngredientSlot, alchemyRecipe.getFuelItem());
        for(Integer slot : HyperAlchemy.getInstance().getInventories().secondaryIngredientSlots)
            setItem(slot, alchemyRecipe.getInputItem());
        for(Integer slot : HyperAlchemy.getInstance().getInventories().specialDecorationSlots)
            setItem(slot, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().specialDecoration));
        setItem(HyperAlchemy.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().closeButton));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()))
            return;
        int slot = e.getSlot();
        e.setCancelled(true);
        if(slot == HyperAlchemy.getInstance().getInventories().closeButton.slot){
            player.closeInventory();
        }
    }

}
