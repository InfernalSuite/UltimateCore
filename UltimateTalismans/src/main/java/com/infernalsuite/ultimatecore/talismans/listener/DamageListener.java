package com.infernalsuite.ultimatecore.talismans.listener;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.objects.ImmunityType;
import com.infernalsuite.ultimatecore.talismans.objects.PlayerTalismans;
import com.infernalsuite.ultimatecore.talismans.objects.implementations.ImmunityTalisman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Optional;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.isCancelled()) return;
        if(!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        PlayerTalismans playerTalismans = HyperTalismans.getInstance().getTalismanManager().getPlayerTalismans().get(player.getUniqueId());
        if(playerTalismans == null || playerTalismans.getNormalTalismans().isEmpty()) return;
        try {
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL)
                reduceDamage(playerTalismans, ImmunityType.FALL, e);
            else if(e.getCause() == EntityDamageEvent.DamageCause.LAVA)
                reduceDamage(playerTalismans, ImmunityType.LAVA, e);
            else if(e.getCause() == EntityDamageEvent.DamageCause.POISON)
                reduceDamage(playerTalismans, ImmunityType.POISON, e);
            else if(e.getCause() == EntityDamageEvent.DamageCause.FIRE)
                reduceDamage(playerTalismans, ImmunityType.FIRE, e);
        }catch (Exception ignored){}

    }

    private void reduceDamage(PlayerTalismans playerTalismans, ImmunityType immunityType, EntityDamageEvent e){
        Optional<String> talisman = playerTalismans.hasTalisman(immunityType);
        if(!talisman.isPresent()) return;
        ImmunityTalisman immunityTalisman = (ImmunityTalisman) HyperTalismans.getInstance().getTalismans().getTalismans().get(talisman.get());
        if(immunityTalisman == null) return;
        double damage = e.getDamage();
        double percentage = immunityTalisman.getImmunities().get(immunityType);
        if(percentage <= 0) return;
        double finalReduction = (damage * percentage) / 100;
        e.setDamage(damage - finalReduction);
    }
}
