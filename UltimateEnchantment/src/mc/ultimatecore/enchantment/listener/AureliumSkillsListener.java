package mc.ultimatecore.enchantment.listener;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.configuration.OptionL;
import com.archyx.aureliumskills.skills.Skills;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.api.events.HyperEnchantEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class AureliumSkillsListener implements Listener {

    private final EnchantmentsPlugin plugin;

    @EventHandler
    public void onBrew(HyperEnchantEvent event){
        Player player = event.getPlayer();
        if(player == null) return;
        if(!OptionL.isEnabled(Skills.ENCHANTING)) return;
        if(plugin.getConfiguration().aurelliumXP < 1) return;
        AureliumAPI.addXp(player, Skills.valueOf(plugin.getConfiguration().aurelliumSkill), plugin.getConfiguration().aurelliumXP);
    }
}
