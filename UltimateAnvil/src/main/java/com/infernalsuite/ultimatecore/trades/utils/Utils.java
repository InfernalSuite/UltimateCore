package com.infernalsuite.ultimatecore.trades.utils;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.infernalsuite.ultimatecore.trades.HyperTrades;
import com.infernalsuite.ultimatecore.trades.Item;
import com.infernalsuite.ultimatecore.trades.objects.TradeObject;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static ItemStack changeItemMeta(ItemStack itemStack, ItemMeta oldItemMeta) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(oldItemMeta.getDisplayName());
        meta.setLore(oldItemMeta.getLore());
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public static List<Placeholder> getTradeGUIPlaceholders(TradeObject tradeObject) {
        ItemStack tradeItem = tradeObject.getTradeItem();
        ItemStack costItem = tradeObject.getCostItem();
        return new ArrayList<>(Arrays.asList(
                new Placeholder("money_cost", String.valueOf(tradeObject.getMoneyCost())),
                new Placeholder("cost_item_amount", String.valueOf(costItem == null ? "0" : costItem.getAmount())),
                new Placeholder("displayname", String.valueOf(tradeObject.getDisplayName())),
                new Placeholder("trade_item_amount", String.valueOf(tradeItem.getAmount()))
        ));
    }

    public static void tradeItem(Player player, TradeObject tradeObject){
        Economy eco = HyperTrades.getInstance().getEconomy();
        if(eco == null) return;
        double price = tradeObject.getMoneyCost();
        double playerMoney = eco.getBalance(player);
        if (price > 0.0D && playerMoney >= price || price <= 0) {
            if(tradeObject.getCostItem() != null){
                ItemStack costItem = tradeObject.getCostItem().clone();
                ItemStack tradeItem = tradeObject.getTradeItem().clone();
                ItemStack[] chestContent = player.getInventory().getContents();
                int chestQuantity = Utils.getItemQuantity(chestContent, costItem);
                try {
                    if(chestQuantity >= costItem.getAmount()) {
                        for (ItemStack itemStack : chestContent) {
                            if (itemStack != null && itemStack.isSimilar(costItem)) {
                                ItemStack toRemoveItem = costItem.clone();
                                if (toRemoveItem.getAmount() > itemStack.getMaxStackSize()) {
                                    for (int i = 0; i < toRemoveItem.getAmount(); i++) {
                                        player.getInventory().removeItem(toRemoveItem);
                                        player.getInventory().addItem(tradeItem);
                                    }
                                } else {
                                    toRemoveItem.setAmount(costItem.getAmount());
                                    player.getInventory().removeItem(toRemoveItem);
                                    player.getInventory().addItem(tradeItem);
                                }
                                eco.withdrawPlayer(player, price);
                                Utils.playSound(player, HyperTrades.getInstance().getConfiguration().successTradeSound);
                                return;
                            }
                        }
                    }else{
                        player.sendMessage(StringUtils.color((HyperTrades.getInstance().getMessages()).getMessage("dontRequiredItems")));
                        Utils.playSound(player, HyperTrades.getInstance().getConfiguration().failTradeSound);
                    }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            }else{
                eco.withdrawPlayer(player, price);
                player.getInventory().addItem(tradeObject.getTradeItem());
                Utils.playSound(player, HyperTrades.getInstance().getConfiguration().successTradeSound);
            }
        }else{
            player.sendMessage(StringUtils.color((HyperTrades.getInstance().getMessages()).getMessage("dontRequiredMoney")));
            Utils.playSound(player, HyperTrades.getInstance().getConfiguration().failTradeSound);
        }
    }

    public static void tradeItem(){

    }

    public static void playSound(Player player, String sound){
        if(sound != null && !sound.equals(""))
         player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
    }



    public static int getItemQuantity(ItemStack[] itemStacks, ItemStack itemStackParam) {
        int quantity = 0;
        if (itemStacks == null)
            return quantity;
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                ItemStack tempItemStack = itemStack.clone();
                tempItemStack.setAmount(itemStack.getMaxStackSize());
                if (tempItemStack.isSimilar(itemStackParam))
                    quantity += itemStack.getAmount();
            }
        }
        return quantity;
    }

    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path){
        Item item = new Item();
        if(yamlConfig.contains(path+".material")) item.material = XMaterial.valueOf(yamlConfig.getString(path+".material"));
        if(yamlConfig.contains(path+".title")) item.title = yamlConfig.getString(path+".title");
        if(yamlConfig.contains(path+".lore")) item.lore = yamlConfig.getStringList(path+".lore");
        if(yamlConfig.contains(path+".slot")) item.slot = yamlConfig.getInt(path+".slot");
        if(yamlConfig.contains(path+".tradeAmount")) item.multiplier = yamlConfig.getInt(path+".tradeAmount");
        if(yamlConfig.contains(path+".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path+".isGlowing");
        if(yamlConfig.contains(path+".amount")) item.amount = yamlConfig.getInt(path+".amount");
        if(yamlConfig.contains(path+".command")) item.command = yamlConfig.getString(path+".command");

        return item;
    }


    public static void openGUIAsync(Player player, TradeObject tradeObject){
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperTrades.getInstance(), () -> player.openInventory(tradeObject.getShopAdminGUI().getInventory()),3L);
    }

    public static TradeObject getMultipliedTrade(TradeObject tradeObject, int multiplier){
        ItemStack costItem = tradeObject.getCostItem() == null ? null : tradeObject.getCostItem().clone();
        ItemStack tradeItem = tradeObject.getTradeItem().clone();
        TradeObject newTrade = new TradeObject(tradeObject.getKey(), tradeObject.getDisplayName(), tradeObject.getDescription(), tradeItem, costItem, tradeObject.getSlot(), tradeObject.getPage(), tradeObject.getMoneyCost(), tradeObject.getPermission(), tradeObject.getCategory());
        if(costItem != null){
            double newAmount = ((double) multiplier / (double) tradeItem.getAmount()) * costItem.getAmount();
            costItem.setAmount(Math.max((int) newAmount, 1));
            newTrade.setCostItem(costItem);
        }
        tradeItem.setAmount(multiplier);
        newTrade.setTradeItem(tradeItem);
        double newPrice = multiplier * (newTrade.getMoneyCost() / tradeObject.getTradeItem().getAmount());
        newTrade.setMoneyCost(Math.round(newPrice));
        return newTrade;
    }
}
