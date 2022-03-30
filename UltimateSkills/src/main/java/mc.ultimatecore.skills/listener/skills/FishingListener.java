package mc.ultimatecore.skills.listener.skills;

import lombok.AllArgsConstructor;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.Skill;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.objects.xp.SkillPoint;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

@AllArgsConstructor
public class FishingListener implements Listener {
    
    private final HyperSkills plugin;
    
    @EventHandler
    public void fishingSkill(PlayerFishEvent e) {
        if (e.isCancelled()) return;
        if (e.getCaught() == null || (e.getState() != PlayerFishEvent.State.CAUGHT_FISH && e.getState() != PlayerFishEvent.State.CAUGHT_ENTITY)) return;
        if (e.getState() == PlayerFishEvent.State.CAUGHT_ENTITY && !plugin.getSkillPoints().getFishingCaughtXP().containsKey(Utils.getUpperValue(e.getCaught().getName()))) return;
        SkillType skillType = SkillType.Fishing;
        double xp = plugin.getSkillPoints().fishingXP;
        String key = e.getCaught() == null ? "" : Utils.getUpperValue(e.getCaught().getName());
        if (!key.equals("") && plugin.getSkillPoints().getFishingCaughtXP().containsKey(key)) {
            SkillPoint skillPoint = plugin.getSkillPoints().getFishingCaughtXP().get(key);
            xp = skillPoint.getXp();
            skillType = skillPoint.getSkillType();
        }
        
        Player player = e.getPlayer();
        Skill skill = plugin.getSkills().getAllSkills().get(skillType);
        if (!skill.isEnabled()) return;
        if (player.getGameMode().equals(GameMode.CREATIVE) && !skill.isXpInCreative()) return;
        if (Utils.isInBlockedWorld(player.getWorld().getName(), skillType)) return;
        if (Utils.isInBlockedRegion(player.getLocation(), skillType)) return;
        plugin.getSkillManager().addXP(player.getUniqueId(), skillType, xp);
    }
}
