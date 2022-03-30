package com.infernalsuite.ultimatecore.trades.gui;

import com.infernalsuite.ultimatecore.trades.HyperTrades;
import com.infernalsuite.ultimatecore.trades.utils.StringUtils;
import com.infernalsuite.ultimatecore.trades.objects.TradeObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SelectItemMenu implements GUI {

    private final ItemStack[] itemStacks;

    private final TradeObject tradeObject;

    private final boolean tradeItem;

    public SelectItemMenu(Player player, TradeObject tradeObject, boolean tradeItem) {
        this.tradeObject = tradeObject;
        this.itemStacks = player.getInventory().getContents();
        this.tradeItem = tradeItem;
    }

    @Override
    public boolean saveOnClose() {
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if(e.getCurrentItem() != null){
                if(tradeItem)
                    tradeObject.setTradeItem(e.getCurrentItem());
                else
                    tradeObject.setCostItem(e.getCurrentItem());
                player.openInventory(tradeObject.getShopAdminGUI().getInventory());
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperTrades.getInstance().getInventories().selectItemSize, StringUtils.color(HyperTrades.getInstance().getInventories().selectItemTitle));
        if(itemStacks != null && itemStacks.length > 0){
            for(int i = 0; i<itemStacks.length; i++){
                if(itemStacks[i] != null && itemStacks[i].getType() != Material.AIR)
                    inventory.setItem(i, itemStacks[i]);
            }
        }
        return inventory;
    }
}
