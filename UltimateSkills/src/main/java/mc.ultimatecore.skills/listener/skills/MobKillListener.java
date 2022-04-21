package mc.ultimatecore.skills.listener.skills;

import io.lumine.mythic.api.mobs.*;
import lombok.AllArgsConstructor;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.Skill;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.objects.xp.MobXP;
import mc.ultimatecore.skills.objects.xp.SkillPoint;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@AllArgsConstructor
public class MobKillListener implements Listener {
    private final HyperSkills plugin;

    @EventHandler
    public void mainCombat(EntityDeathEvent e) {
        if(e.getEntity() instanceof Player) return;
        Player player = e.getEntity().getKiller();
        if(e.getEntity().getCustomName() != null) return;
        if(player == null) return;
        String key = Utils.getKey(e.getEntity().getType().toString());
        if(!plugin.getSkillPoints().skillMobsXP.containsKey(key)) return;
        MobXP mobXP = plugin.getSkillPoints().skillMobsXP.get(key);
        SkillType skillType = mobXP.getSkillType();
        Skill skill = plugin.getSkills().getAllSkills().get(skillType);
        if (!skill.isEnabled()) return;
        if (player.getGameMode().equals(GameMode.CREATIVE) && !skill.isXpInCreative()) return;
        if(Utils.isInBlockedWorld(e.getEntity().getWorld().getName(), skillType)) return;
        if(Utils.isInBlockedRegion(e.getEntity().getLocation(), skillType)) return;
        plugin.getSkillManager().addXP(player.getUniqueId(), skillType, mobXP.getXp());
    }


    @EventHandler
    public void specialCombat(EntityDeathEvent e) {
        if(plugin.getAddonsManager().getMythicMobs() == null) return;
        if(e.getEntity() instanceof Player) return;
        if(e.getEntity().getKiller() == null) return;
        Player player = e.getEntity().getKiller();
        if(player == null) return;
        MythicMob activeMob = plugin.getAddonsManager().getMythicMobs().getMythicMobEntity(e.getEntity());
        if(activeMob == null) return;
        if(!plugin.getSkillPoints().skillEpicMobsXP.containsKey(activeMob.getInternalName())) return;
        SkillPoint epicMobXP = plugin.getSkillPoints().skillEpicMobsXP.get(activeMob.getInternalName());
        SkillType skillType = epicMobXP.getSkillType();
        Skill skill = plugin.getSkills().getAllSkills().get(skillType);
        if (!skill.isEnabled()) return;
        if (player.getGameMode().equals(GameMode.CREATIVE) && !skill.isXpInCreative()) return;
        if(Utils.isInBlockedWorld(e.getEntity().getWorld().getName(), skillType)) return;
        if(Utils.isInBlockedRegion(e.getEntity().getLocation(), skillType)) return;

        plugin.getSkillManager().addXP(player.getUniqueId(), skillType, epicMobXP.getXp());
    }
}
