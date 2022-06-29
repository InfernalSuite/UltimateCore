package mc.ultimatecore.pets.listeners.pets;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.playerdata.User;
import mc.ultimatecore.skills.api.events.SkillsXPGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class SkillsHookListener implements Listener {

    private final HyperPets plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreak(SkillsXPGainEvent e) {
        Player player = e.getPlayer();
        User user = plugin.getUserManager().getUser(player);
        if(user.getPlayerPet() == null) {
            return;
        }
        if(plugin.getConfiguration().skillsXP <= 0D) {
            return;
        }

        plugin.getPetsLeveller().addXP(player, plugin.getConfiguration().skillsXP);
    }

}
