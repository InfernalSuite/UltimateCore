package mc.ultimatecore.runes.listeners.skills;

import lombok.AllArgsConstructor;
import mc.ultimatecore.runes.api.events.RuneTableUseEvent;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.Skill;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class HyperRunesListener implements Listener {
    
    @EventHandler
    public void mainCombat(RuneTableUseEvent e) {
        SkillType skillType = SkillType.Runecrafting;
        Player player = e.getPlayer();
        Skill skill = HyperSkills.getInstance().getSkills().getAllSkills().get(skillType);
        if (!skill.isEnabled()) return;
        if (player.getGameMode().equals(GameMode.CREATIVE) && !skill.isXpInCreative()) return;
        if (Utils.isInBlockedWorld(player.getWorld().getName(), skillType)) return;
        if (Utils.isInBlockedRegion(player.getLocation(), skillType)) return;
        HyperSkills.getInstance().getSkillManager().addXP(player.getUniqueId(), skillType, HyperSkills.getInstance().getSkillPoints().runeCraftingXP);
    }
}
