package com.infernalsuite.ultimatecore.skills.listener.perks;

import com.infernalsuite.ultimatecore.skills.api.events.SkillsDamageEvent;
import com.infernalsuite.ultimatecore.skills.managers.IndicatorType;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.DamageIndicator;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class DamageListener implements Listener {

    private final List<Double> random = new ArrayList<Double>(){{
        add(0.1);
        add(0.2);
        add(0.25);
    }};

    private final HyperSkills plugin;

    @EventHandler(priority = EventPriority.NORMAL)
    public void combatPerks(EntityDamageByEntityEvent e) {
        if(e.isCancelled()) return;
        if (e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) {
            manageDamage((Player) e.getDamager(), e);
        }else if (e.getDamager() instanceof Arrow && !(e.getEntity() instanceof Player)) {
            Arrow arrow = (Arrow) e.getDamager();
            if(arrow.getShooter() instanceof Player)
                manageDamage((Player) arrow.getShooter(), e);
        }
    }

    @EventHandler
    public void indicatorListener(SkillsDamageEvent e) {
        if(e.isCancelled()) return;
        DamageIndicator damageIndicator = plugin.getConfiguration().indicators.get(e.getIndicatorType());
        String key = Utils.getKey(e.getVictim().getType().toString());
        if(!damageIndicator.isActive()) return;
        if(plugin.getConfiguration().indicatorBlackList.contains(key)) return;
        spawnStand(damageIndicator, e);
    }


    private void manageDamage(Player player, EntityDamageByEntityEvent e){
        IndicatorType indicatorType = IndicatorType.NORMAL;
        //Critic Damage and Critic Chance
        int per = (int) plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.Crit_Chance);
        int random = new Random().nextInt(100);
        double finalDamage = e.getDamage();
        if(random <= per) {
            finalDamage+=plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.Crit_Damage);
            indicatorType = IndicatorType.CRITIC;
        }
        //Strength
        double strength = plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.Strength);
        if(strength >= 5)
            finalDamage+=(int) (strength / 5);
        e.setDamage(finalDamage);
        Bukkit.getPluginManager().callEvent(new SkillsDamageEvent(player, e.getEntity(), indicatorType, finalDamage));

    }


    @EventHandler
    public void worldUnload(ChunkUnloadEvent e){
        for(Entity entity : e.getChunk().getEntities()){
            if(!(entity instanceof ArmorStand)) continue;
            ArmorStand armorStand = (ArmorStand) entity;
            String name = armorStand.getName();
            if(name == null || !name.equals("hc_damageindicator")) continue;
            entity.remove();
        }
    }

    private void spawnStand(DamageIndicator damageIndicator, SkillsDamageEvent e){
        Location newLocation = e.getVictim().getLocation().clone();
        newLocation.add(random.get(Utils.getRandom(2)), 0, random.get(Utils.getRandom(2)));
        newLocation.subtract(random.get(Utils.getRandom(2)), 1, random.get(Utils.getRandom(2)));
        ArmorStand dmgIndicator = newLocation.getWorld().spawn(newLocation, ArmorStand.class);
        dmgIndicator.setVisible(false);
        dmgIndicator.setGravity(false);
        dmgIndicator.setCustomNameVisible(true);
        dmgIndicator.setCustomName(Utils.translatedDegradeText(damageIndicator, e.getDamage()));
        removeSync(dmgIndicator);
    }

    private void removeSync(ArmorStand armorStand){
        Bukkit.getScheduler().runTaskLater(plugin, armorStand::remove, 20L);
    }
}
