package com.infernalsuite.ultimatecore.trades.gui;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.trades.HyperTrades;
import com.infernalsuite.ultimatecore.trades.enums.EditType;
import com.infernalsuite.ultimatecore.trades.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.trades.utils.Placeholder;
import com.infernalsuite.ultimatecore.trades.utils.StringUtils;
import com.infernalsuite.ultimatecore.trades.objects.TradeObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class TradesSetupGUI implements GUI {

    private final TradeObject tradeObject;

    public TradesSetupGUI(TradeObject tradeObject) {
        super();
        this.tradeObject = tradeObject;
    }

    @Override
    public boolean saveOnClose() {
        return true;
    }

    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == HyperTrades.getInstance().getInventories().costItem.slot){
                player.openInventory(new SelectItemMenu(player, tradeObject, false).getInventory());
            }else if (e.getSlot() == HyperTrades.getInstance().getInventories().tradeItem.slot){
                player.openInventory(new SelectItemMenu(player, tradeObject, true).getInventory());
            }else if (e.getSlot() == HyperTrades.getInstance().getInventories().tradePermission.slot){
                HyperTrades.getInstance().getTradesManager().setSetupMode(player.getUniqueId(), tradeObject.getKey(), EditType.PERMISSION);
                player.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("typePermissionMessage").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
                player.closeInventory();
            }else if (e.getSlot() == HyperTrades.getInstance().getInventories().moneyCost.slot){
                HyperTrades.getInstance().getTradesManager().setSetupMode(player.getUniqueId(), tradeObject.getKey(), EditType.MONEY_COST);
                player.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("typeCostMessage").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
                player.closeInventory();
            }else if (e.getSlot() == HyperTrades.getInstance().getInventories().tradePage.slot){
                HyperTrades.getInstance().getTradesManager().setSetupMode(player.getUniqueId(), tradeObject.getKey(), EditType.TRADE_PAGE);
                player.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("typePageMessage").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
                player.closeInventory();
            }else if (e.getSlot() == HyperTrades.getInstance().getInventories().tradeSlot.slot){
                HyperTrades.getInstance().getTradesManager().setSetupMode(player.getUniqueId(), tradeObject.getKey(), EditType.TRADE_SLOT);
                player.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("typeSlotMessage").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
                player.closeInventory();
            }else if (e.getSlot() == HyperTrades.getInstance().getInventories().categorySlot.slot){
                HyperTrades.getInstance().getTradesManager().setSetupMode(player.getUniqueId(), tradeObject.getKey(), EditType.CATEGORY);
                player.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("typeCategoryMessage").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
                player.closeInventory();
            }else if(e.getSlot() == HyperTrades.getInstance().getInventories().goBack.slot){
                player.closeInventory();
            }
        }
    }




    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperTrades.getInstance().getInventories().tradeSetupGUISize, StringUtils.color(HyperTrades.getInstance().getInventories().tradeSetupGUITitle));
        ItemStack costItem = tradeObject.getCostItem() == null ? XMaterial.BEACON.parseItem() : tradeObject.getCostItem();
        ItemStack tradeItem = tradeObject.getTradeItem() == null ? XMaterial.EMERALD.parseItem() : tradeObject.getTradeItem();
        inventory.setItem(HyperTrades.getInstance().getInventories().costItem.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().costItem,
                Collections.singletonList(new Placeholder("item", "")), costItem));
        inventory.setItem(HyperTrades.getInstance().getInventories().tradeItem.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().tradeItem,
                Collections.singletonList(new Placeholder("item", "")), tradeItem));
        inventory.setItem(HyperTrades.getInstance().getInventories().tradeKey.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().tradeKey, Collections.singletonList(new Placeholder("key", tradeObject.getKey())) ));
        inventory.setItem(HyperTrades.getInstance().getInventories().tradePermission.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().tradePermission, Collections.singletonList(new Placeholder("permission", tradeObject.getPermission())) ));
        inventory.setItem(HyperTrades.getInstance().getInventories().moneyCost.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().moneyCost, Collections.singletonList(new Placeholder("money_cost", String.valueOf(tradeObject.getMoneyCost()))) ));
        inventory.setItem(HyperTrades.getInstance().getInventories().tradePage.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().tradePage, Collections.singletonList(new Placeholder("page", String.valueOf(tradeObject.getPage()))) ));
        inventory.setItem(HyperTrades.getInstance().getInventories().tradeSlot.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().tradeSlot, Collections.singletonList(new Placeholder("slot", String.valueOf(tradeObject.getSlot()))) ));
        inventory.setItem(HyperTrades.getInstance().getInventories().categorySlot.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().categorySlot, Collections.singletonList(new Placeholder("category", String.valueOf(tradeObject.getCategory()))) ));
        inventory.setItem(HyperTrades.getInstance().getInventories().goBack.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().goBack));
        return inventory;

    }
}
