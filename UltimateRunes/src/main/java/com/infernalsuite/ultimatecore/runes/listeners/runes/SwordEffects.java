package com.infernalsuite.ultimatecore.runes.listeners.runes;

import com.infernalsuite.ultimatecore.runes.managers.RuneEffectManager;
import com.infernalsuite.ultimatecore.runes.objects.Rune;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.runes.HyperRunes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class SwordEffects implements Listener {

    @EventHandler
    public void damageEvents(EntityDeathEvent e) {
        if(e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            ItemStack itemStack = p.getItemInHand();
            if (itemStack.getType().toString().contains("SWORD") && itemStack.hasItemMeta() && itemStack.getType() != Material.AIR) {
                NBTItem nbtItem = new NBTItem(itemStack);
                for(Rune rune : HyperRunes.getInstance().getRunes().runes) {
                    if(nbtItem.hasKey(rune.getRuneName())){
                        new RuneEffectManager(rune, e.getEntity()).startEffect();
                        return;
                    }
                }
            }
        }
    }
}
