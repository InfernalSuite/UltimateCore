package mc.ultimatecore.dragon.listeners;

import com.cryptomorin.xseries.*;
import lombok.*;
import mc.ultimatecore.dragon.*;
import mc.ultimatecore.dragon.objects.*;
import mc.ultimatecore.dragon.objects.event.*;
import mc.ultimatecore.dragon.objects.implementations.*;
import mc.ultimatecore.dragon.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.projectiles.*;

@RequiredArgsConstructor
public class DragonListener implements Listener {

    private final HyperDragons plugin;

    @EventHandler
    public void DragonTarget(EntityTargetEvent e) {
        Entity Dragon = e.getEntity();
        Entity Target = e.getTarget();
        if (Dragon instanceof EnderDragon) {
            Location dloc = Dragon.getLocation();
            Location tloc = Target.getLocation();
            Fireball fireball = Dragon.getWorld().spawn(dloc, Fireball.class);
            fireball.setShooter((ProjectileSource) Dragon);
            fireball.setVelocity(Dragon.getLocation().getDirection().multiply(2));
            fireball.setIsIncendiary(false);
            fireball.setYield(0);
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Fireball) { //changed from dragonfireball
            if (((Fireball) event.getEntity()).getShooter() instanceof EnderDragon) {
                if (plugin.getDragonManager().getDragonGame() == null) return;
                if (plugin.getDragonManager().getDragonGame().getDragonEvent() == null) return;
                IDragonEvent iDragonEvent = plugin.getDragonManager().getDragonGame().getDragonEvent().getCurrentEvent();
                if (iDragonEvent != null && !(iDragonEvent instanceof DragonBallEvent))
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDragonRegen(EntityRegainHealthEvent e) {
        if ((e.getEntity() instanceof EnderDragon || e.getEntity() instanceof EnderDragonPart)) {
            EnderDragon dragon = plugin.getDragonManager().getDragonGame().getEnderDragon().get();
            if (e.getEntity().getUniqueId().equals(dragon.getUniqueId()))
                plugin.getDragonManager().getDragonGame().removePlayersDamage(e.getAmount());
        }
    }

    @EventHandler
    public void onDragonBlock(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof EnderDragon)
            e.setCancelled(true);
    }

    @EventHandler
    public void onDragonBlock(EntityExplodeEvent e) {
        if (e.getEntity() instanceof EnderDragon)
            e.setCancelled(true);
    }

    @EventHandler
    public void stopDragonDamage(EntityExplodeEvent event) {
        Entity e = event.getEntity();
        if (e instanceof EnderDragonPart)
            e = ((EnderDragonPart) e).getParent();
        if (e instanceof EnderDragon)
            event.setCancelled(true);
    }


    @EventHandler
    public void onDragonBlock(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof EnderDragon && ((EnderDragon) e.getEntity()).getHealth() < 2)
            e.setCancelled(true);
    }

    @EventHandler
    public void onDragonBlock(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon)
            plugin.getDragonManager().finish();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Fireball) {
            if (((Fireball) e.getEntity()).getShooter() instanceof EnderDragon) {
                if (plugin.getDragonManager().getDragonGame() == null) return;
                if (plugin.getDragonManager().getDragonGame().getDragonEvent() == null) return;
                Fireball fireball = (Fireball) e.getEntity();
                e.setCancelled(true);
                Location location = fireball.getLocation().clone();
                fireball.remove();
                if (location.getWorld() != null)
                    location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 5, false, false);
            }
        }
    }


    @EventHandler
    public void onExplode(EntityDamageEvent e) {
        if (e.getEntity() instanceof EnderCrystal) {
            if (plugin.getDragonManager().getCrystals().contains(e.getEntity()) && plugin.getConfiguration().inmortalCrystals)
                e.setCancelled(true);
        } else if (e.getEntity() instanceof EnderDragon) {
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
                e.setCancelled(true);
        }

    }


    @EventHandler(priority = EventPriority.LOW)
    public void onExplode(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof EnderDragon)) {
            return;
        }
        if (e.getDamager() instanceof Fireball) { //changed from dragonfireball
            e.setCancelled(true);
            return;
        }

        Player player = null;
        if (e.getDamager() instanceof Player) {
            player = (Player) e.getDamager();
        } else if (e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player) {
            player = (Player) ((Arrow) e.getDamager()).getShooter();
        }

        if (player == null) {
            return;
        }
        double damage = e.getDamage();
        IHyperDragon hyperDragon = plugin.getDragonManager().getDragonGame().getHyperDragon();
        EnderDragon enderDragon = plugin.getDragonManager().getDragonGame().getEnderDragon().get();
        //Managing Player Top Damage
        plugin.getDragonManager().getDragonGame().managePlayerDamage(player, damage);
        //Managing last player
        plugin.getDragonManager().setLast(player.getUniqueId());
        //Managing Dragon Health
        if (!(hyperDragon instanceof HyperDragon) || !(enderDragon.getHealth() > 0.0D)) {
            return;
        }
        e.setCancelled(true);
        enderDragon.setHealth(Utils.getNewHealth(hyperDragon.getHealth(), enderDragon.getHealth(), damage));
        enderDragon.playEffect(EntityEffect.HURT);
        enderDragon.getWorld().playSound(enderDragon.getLocation(), XSound.ENTITY_ENDER_DRAGON_HURT.parseSound(), 100.0F, 1.0F);

    }
}