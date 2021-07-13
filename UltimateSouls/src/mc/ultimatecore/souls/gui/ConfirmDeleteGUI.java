package mc.ultimatecore.souls.gui;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.objects.Soul;
import mc.ultimatecore.souls.utils.InventoryUtils;
import mc.ultimatecore.souls.utils.Placeholder;
import mc.ultimatecore.souls.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;
import java.util.Optional;

public class ConfirmDeleteGUI extends GUI implements Listener {
    
    private int soulID;
    
    public ConfirmDeleteGUI() {
        super(27, HyperSouls.getInstance().getInventories().confirmDeleteGUI);
        HyperSouls.getInstance().registerListeners(this);
        this.soulID = 0;
    }
    
    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        
        Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
        if (soulID != -1 && !soul.isPresent()) return;
        
        setItem(12, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().confirmDelete, Collections.singletonList(new Placeholder("soul_id", soulID != -1 ? String.valueOf(soulID) : "All Souls"))));
        setItem(14, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().cancelDelete));
        
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
                } else if (e.getSlot() == 12) {
                    if (soulID == -1) {
                        HyperSouls.getInstance().getSouls().souls.clear();
                        player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("succesfullyRemovedAll").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
                    } else {
                        Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
                        if (soul.isPresent() && HyperSouls.getInstance().getSouls().removeSoul(soul.get()))
                            player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("succesfullyRemoved").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix).replace("%soul_id%", String.valueOf(soulID))));
                        
                    }
                    player.openInventory(HyperSouls.getInstance().getAllSoulsGUI().get(1).getInventory());
                } else if (e.getSlot() == 14) {
                    player.openInventory(HyperSouls.getInstance().getSoulEditGUI().getInventory());
                }
            }
        }
    }
    
}
