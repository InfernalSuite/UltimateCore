package com.infernalsuite.ultimatecore.souls.gui;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.Item;
import com.infernalsuite.ultimatecore.souls.objects.Soul;
import com.infernalsuite.ultimatecore.souls.objects.SoulParticle;
import com.infernalsuite.ultimatecore.souls.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.souls.utils.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddParticleGUI extends GUI implements Listener {
    
    private int soulID;
    
    private final Map<Integer, SoulParticle> soulParticleSlots = new HashMap<>();
    
    public AddParticleGUI() {
        super(27, HyperSouls.getInstance().getInventories().particleGUITitle);
        HyperSouls.getInstance().registerListeners(this);
        this.soulID = 0;
    }
    
    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        
        Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
        if (soulID != -1 && !soul.isPresent()) return;
        
        int slot = 0;
        for (Item item : HyperSouls.getInstance().getInventories().soulParticleGUI.keySet()) {
            SoulParticle soulParticle = HyperSouls.getInstance().getInventories().soulParticleGUI.get(item);
            String isSame = soul.isPresent() && soul.get().getParticle() != null ? String.valueOf(soul.get().getParticle().equals(soulParticle)) : "false";
            setItem(slot, InventoryUtils.makeItem(item, Collections.singletonList(new Placeholder("status", soulID != -1 ? isSame : "false"))));
            soulParticleSlots.put(slot, soulParticle);
            slot++;
        }
        setItem(21, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().previousPage));
        setItem(22, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().closeButton));
        
        
    }
    
    public void changeSettings(int newID) {
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
                } else {
                    for (Integer slot : soulParticleSlots.keySet()) {
                        if (e.getSlot() == slot) {
                            if (soulID == -1) {
                                for (Soul soul : HyperSouls.getInstance().getSouls().souls.values())
                                    soul.setParticle(soulParticleSlots.get(slot));
                            } else {
                                Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
                                if (!soul.isPresent()) return;
                                soul.get().setParticle(soulParticleSlots.get(slot));
                            }
                        }
                    }
                }
            }
        }
    }
    
}
