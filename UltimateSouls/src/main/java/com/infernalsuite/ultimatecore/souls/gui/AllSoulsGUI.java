package com.infernalsuite.ultimatecore.souls.gui;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.objects.Soul;
import com.infernalsuite.ultimatecore.souls.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.souls.utils.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;


public class AllSoulsGUI extends GUI implements Listener {
    
    private int page;
    
    private final HashMap<Integer, Integer> soulSlots;
    
    public AllSoulsGUI(int page) {
        super(54, HyperSouls.getInstance().getInventories().allSoulsGUITitle);
        this.page = page;
        this.soulSlots = new HashMap<>();
        HyperSouls.getInstance().registerListeners(this);
    }
    
    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        List<Soul> souls = new ArrayList<>(HyperSouls.getInstance().getSouls().souls.values());
        int slot = 0;
        int i = 45 * (page - 1);
        while (slot < 45) {
            if (souls.size() > i && i >= 0) {
                Soul soul = souls.get(i);
                setItem(slot, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().soulItem, Collections.singletonList(new Placeholder("soul_id", String.valueOf(soul.getId())))));
                soulSlots.put(slot, soul.getId());
                slot++;
                i++;
            } else {
                setItem(slot, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().background));
                slot++;
            }
        }
        setItem(HyperSouls.getInstance().getInventories().previousPage.slot, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().previousPage));
        setItem(HyperSouls.getInstance().getInventories().nextPage.slot, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().nextPage));
        setItem(HyperSouls.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().closeButton));
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getPage() {
        return this.page;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                int slot = e.getSlot();
                if (e.getSlot() == HyperSouls.getInstance().getInventories().closeButton.slot) {
                    player.closeInventory();
                } else if (slot <= 45 && soulSlots.containsKey(slot)) {
                    Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulSlots.get(slot));
                    if (!soul.isPresent()) return;
                    HyperSouls.getInstance().getSoulEditGUI().changeSoulID(soul.get().getId());
                    player.openInventory(HyperSouls.getInstance().getSoulEditGUI().getInventory());
                } else if (slot == HyperSouls.getInstance().getInventories().nextPage.slot && HyperSouls.getInstance().getAllSoulsGUI().containsKey(page + 1)) {
                    player.openInventory(HyperSouls.getInstance().getAllSoulsGUI().get(page + 1).getInventory());
                } else if (slot == HyperSouls.getInstance().getInventories().previousPage.slot && HyperSouls.getInstance().getAllSoulsGUI().containsKey(page - 1)) {
                    player.openInventory(HyperSouls.getInstance().getAllSoulsGUI().get(page - 1).getInventory());
                }
            }
        }
    }
    
    
}
