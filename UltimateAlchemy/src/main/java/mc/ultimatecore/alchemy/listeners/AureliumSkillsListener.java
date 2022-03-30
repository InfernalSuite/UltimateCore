package mc.ultimatecore.alchemy.listeners;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.configuration.OptionL;
import com.archyx.aureliumskills.skills.Skills;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.api.events.HyperBrewEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class AureliumSkillsListener implements Listener {

    private final HyperAlchemy plugin;

    @EventHandler
    public void onBrew(HyperBrewEvent event){
        Player player = event.getPlayer();
        if(player == null) return;
        if(!OptionL.isEnabled(Skills.ALCHEMY)) return;
        if(plugin.getConfiguration().aurelliumXP < 1) return;
        AureliumAPI.addXp(player, Skills.valueOf(plugin.getConfiguration().aurelliumSkill), plugin.getConfiguration().aurelliumXP);
    }
}
