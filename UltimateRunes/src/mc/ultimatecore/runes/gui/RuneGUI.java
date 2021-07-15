package mc.ultimatecore.runes.gui;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.runes.HyperRunes;
import mc.ultimatecore.runes.objects.Rune;
import mc.ultimatecore.runes.utils.InventoryUtils;
import mc.ultimatecore.runes.utils.Placeholder;
import mc.ultimatecore.runes.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.HashMap;

public class RuneGUI extends GUI implements Listener {

    private final HashMap<Integer, String> runesSlot;

    public RuneGUI() {
        super(HyperRunes.getInstance().getInventories().getRunesSize(), HyperRunes.getInstance().getInventories().getRunesTitle());
        runesSlot = new HashMap<>();
        HyperRunes.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
            int slot = 0;
            for(Rune rune : HyperRunes.getInstance().getRunes().runes){
                if(getInventory().getItem(slot) != null && getInventory().getItem(slot).getType().equals(XMaterial.PLAYER_HEAD.parseMaterial())) continue;
                setItem(slot, InventoryUtils.makeItem(HyperRunes.getInstance().getRunes().getRuneItem(rune), Arrays.asList(
                        new Placeholder("rune_level", Utils.toRoman(1))
                        , new Placeholder("required_level", String.valueOf(rune.getRequiredLevel(1))))));
                runesSlot.put(slot, rune.getRuneName());
                slot++;
            }
            setItem(HyperRunes.getInstance().getInventories().getCloseButton().slot, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getCloseButton()));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                if (e.getSlot() == HyperRunes.getInstance().getInventories().getCloseButton().slot) {
                    player.closeInventory();
                } else {
                   if(runesSlot.containsKey(e.getSlot()))
                       player.getInventory().addItem(HyperRunes.getInstance().getRunes().getRune(runesSlot.get(e.getSlot()), 1));
                }
            }
        }
    }
}
