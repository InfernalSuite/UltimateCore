package com.infernalsuite.ultimatecore.reforge.gui;

import com.infernalsuite.ultimatecore.reforge.HyperReforge;
import com.infernalsuite.ultimatecore.reforge.User;
import com.infernalsuite.ultimatecore.reforge.api.events.ItemReforgeEvent;
import com.infernalsuite.ultimatecore.reforge.enums.ReforgeState;
import com.infernalsuite.ultimatecore.reforge.managers.GUIManager;
import com.infernalsuite.ultimatecore.reforge.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.reforge.utils.StringUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public class ReforgeGUI extends GUI implements Listener {

    private final GUIManager guiManager;

    private final HyperReforge plugin;

    public ReforgeGUI(User user, HyperReforge plugin) {
        super(plugin.getInventories().getReforgeMenuSize(), plugin.getInventories().getReforgeMenuTitle(), new HashSet<>(Arrays.asList(0,9,18,27,36,8,17,26,35,44,22,13)));
        this.guiManager = new GUIManager(getInventory(), UUID.fromString(user.player));
        guiManager.updateReforge();
        this.plugin = plugin;
        plugin.registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        setItem(plugin.getInventories().getCloseButton().slot, InventoryUtils.makeItem(plugin.getInventories().getCloseButton()));

    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        Inventory inventory = e.getInventory();
        if(!inventory.equals(getInventory())) return;
        guiManager.updateReforge();
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getInventory().equals(getInventory())){
            Player player = (Player) e.getPlayer();
            if(getInventory().getItem(13) != null){
                ItemStack itemStack = getInventory().getItem(13).clone();
                getInventory().setItem(13, null);
                player.getInventory().addItem(itemStack);
            }
            guiManager.updateReforge();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
            Player player = (Player) e.getWhoClicked();
            if(e.getClickedInventory().equals(getInventory())){
                if (e.getSlot() == plugin.getInventories().getCloseButton().slot) {
                    e.setCancelled(true);
                    player.closeInventory();
                }else if (e.getSlot() == 22 || e.getSlot() == 13) {
                    if(e.getSlot() == 22) {
                        e.setCancelled(true);
                        if(guiManager.getReforgeState() != null && guiManager.getReforgeState() != ReforgeState.INCOMPATIBLE_ITEMS){
                            ItemReforgeEvent event = new ItemReforgeEvent(player, guiManager.getReforgeState());
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (event.isCancelled())
                                return;
                            double cost = guiManager.getCost();
                            Economy economy = plugin.getEconomy();
                            if(economy != null){
                                if(!guiManager.isFusing()){
                                    if(economy.getBalance(player) > cost) {
                                        economy.withdrawPlayer(player, cost);
                                        guiManager.fuseItems(player);
                                    }else{
                                        player.sendMessage(StringUtils.color(plugin.getMessages().getMessage("moneyErrorMessage").replace("%prefix%", HyperReforge.getInstance().getConfiguration().prefix)));
                                    }
                                }
                            }else{
                                Bukkit.getLogger().warning("[HyperReforge] Economy wasn't found!");
                            }
                        }
                        return;
                    }
                }else{
                    e.setCancelled(true);
                }
            }
            guiManager.updateReforge();
    }

}
