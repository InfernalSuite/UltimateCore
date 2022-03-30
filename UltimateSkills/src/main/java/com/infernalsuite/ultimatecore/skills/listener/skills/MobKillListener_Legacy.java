package com.infernalsuite.ultimatecore.skills.listener.skills;

import com.infernalsuite.ultimatecore.skills.objects.xp.MobXP;
import com.infernalsuite.ultimatecore.skills.objects.xp.SkillPoint;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.Skill;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@AllArgsConstructor
public class MobKillListener_Legacy implements Listener {

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
        if(e.getEntity() instanceof Skeleton){
            Skeleton skeleton = (Skeleton) e.getEntity();
            if(skeleton.getSkeletonType().equals(Skeleton.SkeletonType.WITHER) && mobXP.getData() != 1)
                return;
        }
        SkillType skillType = mobXP.getSkillType();
        Skill skill = plugin.getSkills().getAllSkills().get(skillType);
        if (!skill.isEnabled()) return;
        if (player.getGameMode().equals(GameMode.CREATIVE) && !skill.isXpInCreative()) return;
        if(Utils.isInBlockedWorld(e.getEntity().getWorld().getName(), skillType)) return;
        if(Utils.isInBlockedRegion(e.getEntity().getLocation(), skillType)) return;
        HyperSkills.getInstance().getSkillManager().addXP(player.getUniqueId(), skillType, mobXP.getXp());
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
