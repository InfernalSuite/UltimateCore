package com.infernalsuite.ultimatecore.pets.listeners.pets;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.playerdata.User;
import com.infernalsuite.ultimatecore.skills.api.events.SkillsXPGainEvent;
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
        if(user.getPlayerPet() == null) return;
        if(plugin.getConfiguration().skillsXP <= 0) return;
        plugin.getPetsLeveller().addXP(player, plugin.getConfiguration().skillsXP);
    }

}
