package com.infernalsuite.ultimatecore.skills.listener.perks;

import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@RequiredArgsConstructor
public class DefenseListener implements Listener {

    private final HyperSkills plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void healthManager(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if(e.isCancelled())
                return;
            if(plugin.getAddonsManager().getCitizensAPIManager() != null && plugin.getAddonsManager().getCitizensAPIManager().isNPCEntity(e.getEntity())) return;
            Player player = ((Player)e.getEntity());
            double health = plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.Health);
            double defense = plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.Defense);
            double newDamage = Utils.getDamage(defense, health, e.getDamage());
            e.setDamage(newDamage);
        }
    }
}
