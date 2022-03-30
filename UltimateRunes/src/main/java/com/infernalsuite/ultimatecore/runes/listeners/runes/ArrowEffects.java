package com.infernalsuite.ultimatecore.runes.listeners.runes;

import com.infernalsuite.ultimatecore.runes.managers.RuneEffectManager;
import com.infernalsuite.ultimatecore.runes.objects.Rune;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.runes.HyperRunes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class ArrowEffects implements Listener {

    @EventHandler
    public void arrowEffect(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            ItemStack itemStack = p.getItemInHand();
            if(itemStack != null && itemStack.hasItemMeta()) {
                NBTItem nbtItem = new NBTItem(itemStack);
                for(Rune rune : HyperRunes.getInstance().getRunes().runes){
                    if(nbtItem.hasKey(rune.getRuneName())){
                        new RuneEffectManager(rune, e.getProjectile()).startEffect();
                        return;
                    }
                }
            }
        }
    }
}
