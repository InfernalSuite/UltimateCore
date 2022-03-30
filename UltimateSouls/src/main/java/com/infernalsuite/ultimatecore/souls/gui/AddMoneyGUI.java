package com.infernalsuite.ultimatecore.souls.gui;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.objects.Soul;
import com.infernalsuite.ultimatecore.souls.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.souls.utils.Placeholder;
import com.infernalsuite.ultimatecore.souls.utils.StringUtils;
import com.infernalsuite.ultimatecore.souls.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collections;
import java.util.Optional;

public class AddMoneyGUI extends GUI implements Listener {
    
    private int soulID;
    
    private double soulMoney;
    
    private Player player;
    
    public AddMoneyGUI() {
        super(27, HyperSouls.getInstance().getInventories().addMoneyGUITitle);
        HyperSouls.getInstance().registerListeners(this);
        this.soulID = 0;
        this.soulMoney = 0;
    }
    
    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        
        Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
        if (soulID != -1 && !soul.isPresent()) return;
        
        soulMoney = soulID == -1 ? 0 : soul.get().getMoneyReward();
        
        setItem(12, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().moneyGUIRemoveMoney));
        setItem(13, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().moneyGUIChangeMoney, Collections.singletonList(new Placeholder("money", String.valueOf(soulMoney)))));
        setItem(14, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().moneyGUIAddMoney));
        
        
        setItem(21, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().previousPage));
        setItem(22, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().closeButton));
        
        
    }
    
    public void changeSettings(Player player, int newID) {
        this.player = player;
        this.soulID = newID;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                if (e.getSlot() == 21) {
                    player.closeInventory();
                    player.openInventory(HyperSouls.getInstance().getSoulEditGUI().getInventory());
                } else if (e.getSlot() == 22) {
                    player.closeInventory();
                } else if (e.getSlot() == 12) {
                    changeMoney(-100, false, true);
                } else if (e.getSlot() == 13) {
                    player.closeInventory();
                    player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("editorMode").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
                    this.player = player;
                } else if (e.getSlot() == 14) {
                    changeMoney(100, false, true);
                }
            }
        }
    }
    
    @EventHandler
    public void checkChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (this.player != null && player == this.player) {
            event.setCancelled(true);
            String message = event.getMessage();
            if (message.contains("leave") || message.contains("exit")) {
                this.player = null;
                Utils.openGUIAsync(player, getInventory());
            } else {
                try {
                    int num = Integer.parseInt(message);
                    if (num >= 1) {
                        changeMoney(num, true, false);
                        Utils.openGUIAsync(player, getInventory());
                    } else {
                        player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("invalidNumber").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
                    }
                    this.player = null;
                } catch (NumberFormatException e) {
                    player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("invalidNumber").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
                }
            }
            this.player = null;
        }
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (event.getPlayer() == this.player) this.player = null;
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerKickEvent event) {
        if (event.getPlayer() == this.player) this.player = null;
    }
    
    private void changeMoney(int num, boolean message, boolean addMoney) {
        if (soulID != -1) {
            Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
            if (soul.isPresent()) {
                if (addMoney) soul.get().setMoneyReward(soul.get().getMoneyReward() + num);
                if (!addMoney) soul.get().setMoneyReward(num);
                if (message) player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("changedMoney").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix).replace("%money%", String.valueOf(num))));
            }
        } else {
            for (Soul soul : HyperSouls.getInstance().getSouls().souls.values()) {
                if (addMoney) soul.setMoneyReward(soul.getMoneyReward() + num);
                if (!addMoney) soul.setMoneyReward(num);
            }
            if (message) player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("changedMoney").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix).replace("%money%", String.valueOf(num))));
        }
    }
    
}
