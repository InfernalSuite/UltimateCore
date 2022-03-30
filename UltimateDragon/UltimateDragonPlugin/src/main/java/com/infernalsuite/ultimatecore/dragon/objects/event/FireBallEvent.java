package com.infernalsuite.ultimatecore.dragon.objects.event;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IDragonEvent;
import com.infernalsuite.ultimatecore.dragon.utils.particle.EnumParticle;
import com.infernalsuite.ultimatecore.dragon.utils.particle.Particle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Set;

public class FireBallEvent extends IDragonEvent {

    private final double amount;
    private final boolean particle;
    private final double damage;
    private ArmorStand armorStand;

    public FireBallEvent(int duration, int repeat, double dragonSpeed, boolean freeze, double amount, boolean particle, double damage) {
        super(duration, repeat, dragonSpeed, freeze);
        this.amount = amount;
        this.particle = particle;
        this.damage = damage;
    }

    @Override
    public void start() {
        time = 0;
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(HyperDragons.getInstance(), () -> {
            checkFreeze();
            makeParticle();
            //Cancelling Event
            if(time >= duration)
                end();
            else
                //Check Event
                if(time % repeat == 0)
                    manageDragon();
            time+=1;
        }, 0, 20);
    }

    @Override
    public void end(){
        super.end();
        if(armorStand != null) Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> armorStand.remove());
    }

    protected void manageDragon(){
        move();
        shootFireballs();
    }

    private void shootFireballs(){
        Set<Player> players = HyperDragons.getInstance().getDragonManager().getEventPlayers();
        if(players.isEmpty() || amount < 1) return;
        int perPlayer = (int) (amount / players.size());
        Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () ->{
            for(Player player : players){
                if(player == null) continue;
                for(int i = 0; i<perPlayer; i++)
                    shootFireball(player, i);
            }
        });
    }

    private void makeParticle(){
        if(!freeze || !particle) return;
        int points = 30;
        double initialRadius = 1;
        Bukkit.getScheduler().runTaskLater(HyperDragons.getInstance(), () -> {
            final Location origin = getDragonHeadLocation();
            if(origin == null) return;
            for(double radius = initialRadius; radius>0; radius-=0.1) {
                for (int i = 0; i < points; i++) {
                    double angle = 2 * Math.PI * i / points;
                    Location point = origin.clone().add(radius * Math.sin(angle), 0.0d, radius * Math.cos(angle));
                    new Particle().sendParticle(Bukkit.getOnlinePlayers(), EnumParticle.FLAME, true, point.getWorld(), (float) point.getX(), (float) point.getY(), (float) point.getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 15);
                }
            }
        }, 1);
    }

    private void shootFireball(Player player, int i) {
        Location location = getDragonHeadLocation();
        Bukkit.getScheduler().runTaskLater(HyperDragons.getInstance(), () -> {
            if (location != null && this.enderDragon != null && player != null && player.isOnline()) {
                spawnStand(location);
                if (this.armorStand == null)
                    return;
                Location loc = player.getLocation().clone();
                Fireball fb = this.armorStand.launchProjectile(Fireball.class, genVec(this.armorStand.getLocation(), loc));
                fb.setMetadata("Fireball", new FixedMetadataValue(HyperDragons.getInstance(), this.damage));
            }
        },20L * i);
    }

    private Vector genVec(Location a, Location b) {
        double dX = a.getX() - b.getX();
        double dY = a.getY() - b.getY();
        double dZ = a.getZ() - b.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);
        return new Vector(x, z, y);
    }

    private Location getDragonHeadLocation() {
        return HyperDragons.getInstance().getNms().getHeadLocation(this.enderDragon.get());
    }

    private void spawnStand(Location spawn) {
        if (this.armorStand != null)
            return;
        this.armorStand = (ArmorStand)spawn.getWorld().spawn(spawn, ArmorStand.class);
        this.armorStand.setSmall(true);
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(false);
        this.armorStand.setInvulnerable(true);
        this.armorStand.setCollidable(false);
    }
}
