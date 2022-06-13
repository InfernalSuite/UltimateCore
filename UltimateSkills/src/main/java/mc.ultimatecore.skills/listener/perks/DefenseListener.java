package mc.ultimatecore.skills.listener.perks;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.utils.Utils;
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
            double health = plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.HEALTH);
            double defense = plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.DEFENSE);
            double newDamage = Utils.getDamage(defense, health, e.getDamage());
            e.setDamage(newDamage);
        }
    }
}
