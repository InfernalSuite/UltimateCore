package com.infernalsuite.ultimatecore.trades.gui;

import com.infernalsuite.ultimatecore.trades.HyperTrades;
import com.infernalsuite.ultimatecore.trades.Item;
import com.infernalsuite.ultimatecore.trades.objects.TradeObject;
import com.infernalsuite.ultimatecore.trades.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.trades.utils.StringUtils;
import com.infernalsuite.ultimatecore.trades.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TradingOptionsGUI implements GUI {
    private final TradeObject tradeObject;

    private final Map<Integer, TradeObject> shopSlots;

    public TradingOptionsGUI(TradeObject tradeObject) {
        this.shopSlots = new HashMap<>();
        this.tradeObject = tradeObject;
    }

    @Override
    public boolean saveOnClose() {
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player)e.getWhoClicked();
            if(e.getSlot() == HyperTrades.getInstance().getInventories().closeButton.slot){
                player.closeInventory();
            }else if(e.getSlot() == HyperTrades.getInstance().getInventories().tradingOptionsPreviousPage.slot){
                player.openInventory(new TradesGUI(player.getUniqueId(), tradeObject.getPage(), tradeObject.getCategory()).getInventory());
            }else{
                if(shopSlots.containsKey(e.getSlot())){
                    Utils.tradeItem(player, shopSlots.get(e.getSlot()));
                }
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperTrades.getInstance().getInventories().tradeOptionsGUISize, StringUtils.color(HyperTrades.getInstance().getInventories().tradeOptionsGUITitle));
        for (Item item : HyperTrades.getInstance().getInventories().tradingOptionsGUI) {
            TradeObject newTrade = Utils.getMultipliedTrade(tradeObject, item.multiplier);
            inventory.setItem(item.slot, InventoryUtils.makeItem(item, newTrade));
            this.shopSlots.put(item.slot, newTrade);
        }
        inventory.setItem(HyperTrades.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().closeButton));
        inventory.setItem(HyperTrades.getInstance().getInventories().tradingOptionsPreviousPage.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().tradingOptionsPreviousPage));
        return inventory;
    }
}
