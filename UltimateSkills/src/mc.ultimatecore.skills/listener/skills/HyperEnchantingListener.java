package mc.ultimatecore.skills.listener.skills;

import lombok.AllArgsConstructor;
import mc.ultimatecore.enchantment.api.events.HyperEnchantEvent;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.Skill;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class HyperEnchantingListener implements Listener {
    private final HyperSkills plugin;

    @EventHandler
    public void hyperEnchantListener(HyperEnchantEvent e) {
        SkillType skillType = SkillType.Enchanting;
        Player player = e.getPlayer();
        Skill skill = plugin.getSkills().getAllSkills().get(skillType);
        if (!skill.isEnabled()) return;
        if (player.getGameMode().equals(GameMode.CREATIVE) && !skill.isXpInCreative()) return;
        if (Utils.isInBlockedWorld(player.getWorld().getName(), skillType)) return;
        if (Utils.isInBlockedRegion(player.getLocation(), skillType)) return;
        plugin.getSkillManager().addXP(player.getUniqueId(), skillType, plugin.getSkillPoints().enchantingXP);
    }
}
