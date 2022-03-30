package com.infernalsuite.ultimatecore.souls.gui;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.objects.Soul;
import com.infernalsuite.ultimatecore.souls.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.souls.utils.Placeholder;
import com.infernalsuite.ultimatecore.souls.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;
import java.util.Optional;

public class SoulEditGUI extends GUI implements Listener {
    
    public int soulID;
    
    private final AddMoneyGUI addMoneyGUI;
    
    private final AddCommandGUI addCommandGUI;
    
    private final AddParticleGUI addParticleGUI;
    
    private final ConfirmDeleteGUI confirmDeleteGUI;
    
    public SoulEditGUI() {
        super(54, HyperSouls.getInstance().getInventories().soulEditGUITitle);
        HyperSouls.getInstance().registerListeners(this);
        this.soulID = 0;
        addMoneyGUI = new AddMoneyGUI();
        addCommandGUI = new AddCommandGUI();
        addParticleGUI = new AddParticleGUI();
        confirmDeleteGUI = new ConfirmDeleteGUI();
    }
    
    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
        if (!soul.isPresent()) return;
        
        
        setItem(4, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().soulLocation, Utils.getSoulEditGUIPlaceHolders(soul.get())));
        setItem(21, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().soulEffect));
        setItem(23, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().soulDelete, Collections.singletonList(new Placeholder("soul_id", String.valueOf(soul.get().getId())))));
        setItem(38, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().addCommand));
        setItem(42, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().addMoney));
        
        
        setItem(48, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().previousPage));
        setItem(49, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().closeButton));
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                if (e.getSlot() == 49) {
                    player.closeInventory();
                } else if (e.getSlot() == 48) {
                    player.openInventory(HyperSouls.getInstance().getAllSoulsGUI().get(1).getInventory());
                } else if (e.getSlot() == 4) {
                    if (soulID == -1) return;
                    Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
                    if (!soul.isPresent()) return;
                    player.teleport(soul.get().getLocation());
                } else if (e.getSlot() == 21) {
                    player.closeInventory();
                    addParticleGUI.changeSettings(soulID);
                    player.openInventory(addParticleGUI.getInventory());
                } else if (e.getSlot() == 23) {
                    player.closeInventory();
                    confirmDeleteGUI.changeSettings(soulID);
                    player.openInventory(confirmDeleteGUI.getInventory());
                } else if (e.getSlot() == 38) {
                    player.closeInventory();
                    addCommandGUI.changeSettings(player, soulID);
                    player.openInventory(addCommandGUI.getInventory());
                } else if (e.getSlot() == 42) {
                    player.closeInventory();
                    addMoneyGUI.changeSettings(player, soulID);
                    player.openInventory(addMoneyGUI.getInventory());
                }
            }
        }
    }
    
    public void changeSoulID(int newID) {
        this.soulID = newID;
    }
    
}
