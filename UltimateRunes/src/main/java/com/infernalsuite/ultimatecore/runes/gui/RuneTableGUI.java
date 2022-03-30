package com.infernalsuite.ultimatecore.runes.gui;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.runes.HyperRunes;
import com.infernalsuite.ultimatecore.runes.api.events.RuneTableUseEvent;
import com.infernalsuite.ultimatecore.runes.enums.RuneState;
import com.infernalsuite.ultimatecore.runes.managers.User;
import com.infernalsuite.ultimatecore.runes.runetable.RuneTableManager;
import com.infernalsuite.ultimatecore.runes.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.runes.utils.StringUtils;
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

public class RuneTableGUI extends GUI implements Listener {

    private final RuneTableManager runeTableManager;

    public RuneTableGUI(User user) {
        super(HyperRunes.getInstance().getInventories().getRuneTableMenuSize(), HyperRunes.getInstance().getInventories().getRuneTableTitle(), new HashSet<>(Arrays.asList(19, 25, 31, 10, 11, 12, 14, 15, 16)));
        this.runeTableManager = new RuneTableManager(getInventory(), UUID.fromString(user.player));
        runeTableManager.checkRuneTable();
        HyperRunes.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if (getInventory().getItem(31) == null) {
            setItem(31, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getRuneInfoItem()));
        }
        if (getInventory().getItem(13) == null) {
            setItem(13, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getRuneButtonItem()));
        }
        setItem(HyperRunes.getInstance().getInventories().getCloseButton().slot, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getCloseButton()));

    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        Inventory inventory = e.getInventory();
        if(!inventory.equals(getInventory())) return;
        if(runeTableManager.isItemToPickup()){
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("itemToPickup")));
            return;
        }
        runeTableManager.checkRuneTable();
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getInventory().equals(getInventory())){
            Player player = (Player) e.getPlayer();
            //item is fusing
            if(runeTableManager.isFusing()) Bukkit.getScheduler().cancelTask(runeTableManager.getTaskID());
            if(runeTableManager.getFirstItem() != null) player.getInventory().addItem(runeTableManager.getFirstItem());
            if(runeTableManager.getSecondItem() != null) player.getInventory().addItem(runeTableManager.getSecondItem());
            getInventory().setItem(19, XMaterial.AIR.parseItem());
            getInventory().setItem(25, XMaterial.AIR.parseItem());
            ItemStack itemStack = getInventory().getItem(31);
            if(runeTableManager.isFusing()) runeTableManager.setFusing(false);
            if(runeTableManager.isItemToPickup()){
                runeTableManager.setItemToPickup(false);
                player.getInventory().addItem(itemStack);
                getInventory().setItem(31, XMaterial.AIR.parseItem());
            }
            runeTableManager.checkRuneTable();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
            Player player = (Player) e.getWhoClicked();
            if(runeTableManager.isFusing()){
                e.setCancelled(true);
                return;
            }
            if(runeTableManager.isItemToPickup()){
                if(e.getClickedInventory().equals(getInventory()) && e.getSlot() == 31){
                    runeTableManager.setItemToPickup(false);
                    runeTableManager.checkRuneTable();
                    return;
                }
                e.setCancelled(true);
                player.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("itemToPickup")));
                return;
            }

            if(e.getClickedInventory().equals(getInventory())){
                if (e.getSlot() == HyperRunes.getInstance().getInventories().getCloseButton().slot) {
                    e.setCancelled(true);
                    player.closeInventory();
                }else if (e.getSlot() != 19 && e.getSlot() != 25) {
                    if(e.getSlot() == 31) {
                        if(runeTableManager.isItemToPickup())
                            return;
                        e.setCancelled(true);
                        return;
                    }else{
                        e.setCancelled(true);
                        if(e.getSlot() == 13){
                            if(runeTableManager.getRuneState() == RuneState.NO_ERROR_ITEMS || runeTableManager.getRuneState() == RuneState.NO_ERROR_RUNES){
                                RuneTableUseEvent event = new RuneTableUseEvent(player, runeTableManager.getRuneState());
                                Bukkit.getServer().getPluginManager().callEvent(event);
                                if (event.isCancelled())
                                    return;
                                runeTableManager.fuseItems();
                            }
                            return;
                        }
                    }
                }
            }
            runeTableManager.checkRuneTable();
    }

}
