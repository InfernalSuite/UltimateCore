package com.infernalsuite.ultimatecore.skills.listener;

import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.TempUser;
import com.infernalsuite.ultimatecore.skills.utils.AttributeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerJoinLeaveListener implements Listener {

    private final HyperSkills plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        try {
            Player player = event.getPlayer();

            if(!plugin.getAddonsManager().isMMOItems())
                AttributeUtils.manageAttribute(player, 20, HyperSkills.getInstance());

            plugin.getAbilitiesManager().addIntoTable(event.getPlayer());
            plugin.getAbilitiesManager().loadPlayerData(event.getPlayer());

            plugin.getPerksManager().addIntoTable(event.getPlayer());
            plugin.getPerksManager().loadPlayerData(event.getPlayer());

            plugin.getSkillManager().addIntoTable(event.getPlayer());
            plugin.getSkillManager().loadPlayerData(event.getPlayer());

            TempUser user = TempUser.getUser(player);
            user.name = player.getName();
            if(HyperSkills.getInstance().getConfiguration().scaledHealth){
                player.setHealthScale(20);
                player.setHealthScaled(false);
            }
        } catch (Exception e) {
            HyperSkills.getInstance().sendErrorMessage(e);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        try {
            Player player = event.getPlayer();

            plugin.getAbilitiesManager().savePlayerData(player, true, true);

            plugin.getPerksManager().savePlayerData(player, true, true);

            plugin.getSkillManager().savePlayerData(player, true, true);

        } catch (Exception e) {
            HyperSkills.getInstance().sendErrorMessage(e);
        }
    }
}
