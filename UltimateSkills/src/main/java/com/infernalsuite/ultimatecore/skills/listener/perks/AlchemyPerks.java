package com.infernalsuite.ultimatecore.skills.listener.perks;

import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class AlchemyPerks implements Listener {
    @EventHandler
    public void playerConsumePotion(PlayerItemConsumeEvent e) {
        if(e.getItem().getType().equals(Material.POTION))
            randomEffect(e.getPlayer());
    }

    private void randomEffect(Player p) {
        int r = new Random().nextInt(100);
        int percentage = (int) HyperSkills.getInstance().getApi().getTotalPerk(p.getUniqueId(), Perk.Potions_Chance);
        if (r < percentage)
            p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effects[new Random().nextInt(6)]), 600, 1));
    }

    private final String[] effects = {"ABSORPTION", "DAMAGE_RESISTANCE", "HEAL", "FIRE_RESISTANCE", "LUCK", "INCREASE_DAMAGE", "REGENERATION"};
}
