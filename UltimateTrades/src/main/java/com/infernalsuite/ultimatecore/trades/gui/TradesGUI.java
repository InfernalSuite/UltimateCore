package com.infernalsuite.ultimatecore.trades.gui;

import com.infernalsuite.ultimatecore.trades.HyperTrades;
import com.infernalsuite.ultimatecore.trades.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.trades.utils.StringUtils;
import com.infernalsuite.ultimatecore.trades.utils.Utils;
import com.infernalsuite.ultimatecore.trades.objects.TradeObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TradesGUI implements GUI {

    private final Map<Integer, TradeObject> tradesSlots;

    private final UUID uuid;

    private final int page;

    private final String category;

    private final HyperTrades plugin = HyperTrades.getInstance();

    public TradesGUI(UUID uuid, int page, String category) {
        this.tradesSlots = new HashMap<>();
        this.uuid = uuid;
        this.page = page;
        this.category = category;
    }

    @Override
    public boolean saveOnClose() {
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            int slot = e.getSlot();
            if(e.getSlot() == HyperTrades.getInstance().getInventories().closeButton.slot) {
                player.closeInventory();
            }else if(e.getSlot() == HyperTrades.getInstance().getInventories().mainMenuNextPage.slot && e.getCurrentItem() != null
                    && e.getCurrentItem().getType() == HyperTrades.getInstance().getInventories().mainMenuNextPage.material.parseMaterial()){
                player.openInventory(new TradesGUI(uuid, page + 1, category).getInventory());
            }else if(e.getSlot() == HyperTrades.getInstance().getInventories().mainMenuPreviousPage.slot && page > 1){
                player.openInventory(new TradesGUI(uuid, page - 1, category).getInventory());
            }else if(e.getSlot() == HyperTrades.getInstance().getInventories().mainMenuBack.slot && HyperTrades.getInstance().getInventories().mainMenuBackEnabled){
                player.closeInventory();
                String command = plugin.getInventories().mainMenuBack.command;
                if(command.contains("%player%"))
                    Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())), 3);
                else
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.performCommand(command), 3);
            }else if(tradesSlots.containsKey(e.getSlot())){
                TradeObject tradeObject = tradesSlots.get(slot);
                if(!player.hasPermission(tradeObject.getPermission())){
                    player.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("noPermission")));
                    return;
                }
                if(e.getClick() == ClickType.LEFT){
                    Utils.tradeItem(player, tradeObject);
                }else if(e.getClick() == ClickType.RIGHT){
                    player.openInventory(tradeObject.getTradingOptionsGUI().getInventory());
                }
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperTrades.getInstance().getInventories().tradesGUISize, StringUtils.color(HyperTrades.getInstance().getInventories().tradesGUITitle));
        for (int i = 0; i < inventory.getSize(); i++) {
            if (!HyperTrades.getInstance().getInventories().tradesGUIExcludedSlots.contains(i))
                inventory.setItem(i, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().background));
        }
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return inventory;
        if(HyperTrades.getInstance().getInventories().mainMenuBackEnabled)
            inventory.setItem(HyperTrades.getInstance().getInventories().mainMenuBack.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().mainMenuBack));
        for(TradeObject tradeObject : HyperTrades.getInstance().getTradesManager().tradeObjects){
            if(tradeObject.getTradeItem() == null) continue;
            if(tradeObject.getCategory().equals(category)){
                if(tradeObject.getPage() == page){
                    if(player.hasPermission(tradeObject.getPermission()))
                        inventory.setItem(tradeObject.getSlot(), InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().unlockedTrade, tradeObject));
                    else
                        inventory.setItem(tradeObject.getSlot(), InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().lockedTrade));
                    tradesSlots.put(tradeObject.getSlot(), tradeObject);
                }else if(tradeObject.getPage() > page){
                    inventory.setItem(HyperTrades.getInstance().getInventories().mainMenuNextPage.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().mainMenuNextPage));
                }
            }
        }
        if(page > 1)
            inventory.setItem(HyperTrades.getInstance().getInventories().mainMenuPreviousPage.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().mainMenuPreviousPage));
        inventory.setItem(HyperTrades.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperTrades.getInstance().getInventories().closeButton));
        return inventory;
    }

}
