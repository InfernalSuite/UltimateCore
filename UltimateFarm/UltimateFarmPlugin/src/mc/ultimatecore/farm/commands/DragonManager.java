package mc.ultimatecore.farm.commands;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.farm.HyperRegions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class DragonManager implements Listener {
    
    private BukkitTask task;
    @Setter
    private EnderDragon enderDragon;
    private final Location location = new Location(Bukkit.getWorld("world"), -238, 108, -377);
    @Getter
    @Setter
    private boolean teleport = false;
    
    public void setDragon(EnderDragon enderDragon) {
        if (this.enderDragon != null) kill();
        this.enderDragon = enderDragon;
        startScheduler();
    }
    
    private void startScheduler() {
        if (task != null) task.cancel();
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(HyperRegions.getInstance(), this::teleport, 0, 20);
    }
    
    public void setTarget(LivingEntity entity) {
        if (enderDragon == null || enderDragon.isDead()) return;
        Mob mob = (Mob) entity;
        mob.setTarget(entity);
        EntityTargetEvent event = new EntityTargetEvent(enderDragon, entity, EntityTargetEvent.TargetReason.CUSTOM);
        Bukkit.getPluginManager().callEvent(event);
        mob.setTarget(entity);
    }
    
    private void teleport() {
        Bukkit.getScheduler().runTask(HyperRegions.getInstance(), () -> {
            if (enderDragon == null || enderDragon.isDead() || !teleport) return;
            double distance = enderDragon.getLocation().distance(location);
            if (distance > 100) Bukkit.getScheduler().runTask(HyperRegions.getInstance(), () -> enderDragon.teleport(location));
        });
    }
    
    public void setPhase(EnderDragon.Phase phase) {
        if (enderDragon == null || enderDragon.isDead()) return;
        enderDragon.setPhase(phase);
    }
    
    public void kill() {
        if (enderDragon != null)
            enderDragon.remove();
    }
    
    public void setAI(boolean st) {
        if (enderDragon == null || enderDragon.isDead()) return;
        enderDragon.setAI(st);
    }
    
    public void shoot(Player player) {
        if (enderDragon == null || enderDragon.isDead()) return;
        Location loc = player.getLocation().clone();
        Location dragon = enderDragon.getLocation().clone();
        Fireball fireball = dragon.getWorld().spawn(dragon, Fireball.class);
        fireball.setShooter(enderDragon);
        fireball.setVelocity(enderDragon.getLocation().getDirection().multiply(2));
        fireball.setIsIncendiary(false);
        fireball.setYield(0);
    }
    
    private Vector genVec(Location a, Location b) {
        Vector to = b.toVector();
        Vector from = a.toVector();
        return to.subtract(from);
    }
    
    @EventHandler
    public void DragonTarget(EntityTargetEvent e) {
        Entity Dragon = e.getEntity();
        Entity Target = e.getTarget();
        if (Dragon instanceof EnderDragon) {
            Target.sendMessage("Test");
            Location dloc = Dragon.getLocation();
            Location tloc = Target.getLocation();
            Fireball fireball = Dragon.getWorld().spawn(dloc, Fireball.class);
            fireball.setShooter((ProjectileSource) Dragon);
            fireball.setVelocity(Dragon.getLocation().getDirection().multiply(2));
            fireball.setIsIncendiary(false);
            fireball.setYield(0);
        }
    }
}
