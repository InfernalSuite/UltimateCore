package mc.ultimatecore.farm.guardians.implementations;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.guardians.Guardian;
import mc.ultimatecore.farm.particle.EnumParticle;
import mc.ultimatecore.farm.skullcreator.SkullCreator;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class LegacyGuardian extends Guardian {
    private BukkitTask floatTask;
    private BukkitTask particleTask;

    public LegacyGuardian(String name, Location location, boolean enable) {
        super(name, location);
        if (enable) this.enable();
    }

    public EulerAngle getAngle(Location dir) {
        double xzLength = Math.sqrt(dir.getX() * dir.getX() + dir.getZ() * dir.getZ());
        double pitch = Math.atan2(xzLength, dir.getY()) - 1.5707963267948966D;
        double yaw = -Math.atan2(dir.getX(), dir.getZ()) + 3.141592653589793D;
        return new EulerAngle(pitch, yaw, 0.0D);
    }

    public Vector getVector(Location loc1, Location loc2) {
        return new Vector(loc2.getX() - loc1.getX(), loc2.getY() - loc1.getY(), loc2.getZ() - loc1.getZ());
    }

    public void remove() {
        if (particleTask != null) this.particleTask.cancel();
        if (floatTask != null) this.floatTask.cancel();
        if (armorStand != null) this.armorStand.remove();
        removeCopies();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void makeLineParticle(Location blockLocation) {
        int space = 2;
        if (armorStand == null) return;
        Location guardianLocation = armorStand.getLocation().clone();
        guardianLocation.add(0, 0.6, 0);
        World world = guardianLocation.getWorld();
        Validate.isTrue(blockLocation.getWorld().equals(world), "Lines cannot be in different worlds!");
        double distance = guardianLocation.distance(blockLocation);
        Vector p1 = guardianLocation.toVector();
        Vector p2 = blockLocation.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double covered = 0;
        for (; covered < distance; p1.add(vector)) {
            particle.sendParticle(new ArrayList<>(Bukkit.getOnlinePlayers()), EnumParticle.FIREWORKS_SPARK, true, armorStand.getLocation().getWorld(), (float) p1.getX(), (float) p1.getY(), (float) p1.getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 1);
            covered += space;
        }
    }

    @Override
    public void enable() {
        removeCopies();
        this.armorStand = location.getWorld().spawn(location, ArmorStand.class);
        this.armorStand.setVisible(false);
        this.armorStand.setMarker(true);
        this.armorStand.setSmall(true);
        this.armorStand.setGravity(false);
        this.armorStand.setArms(true);
        this.armorStand.setRemoveWhenFarAway(false);
        this.armorStand.setCustomName("guardian_" + name);
        this.armorStand.setCustomNameVisible(false);
        this.armorStand.setHelmet(SkullCreator.getSkull(HyperRegions.getInstance().getConfiguration().guardianTexture));
        this.floatTask = (new BukkitRunnable() {

            final double maxY = 0.25D;
            final double minY = -0.25D;
            final int speedAreaMin = 50;
            final int speedAreaMax = 100;
            double internalY = 0.0D;
            double currentY = location.getY();
            boolean floating = false;
            int currentSpeed = 0;
            double currentMultiplier = 0.2D;

            public void run() {

                if (this.currentSpeed >= this.speedAreaMin && this.currentSpeed <= this.speedAreaMax) {
                    if (this.currentSpeed % 10 == 0) {
                        this.currentMultiplier += 0.1D;
                    }

                    if (this.currentMultiplier >= 0.7D) {
                        this.currentMultiplier = 0.7D;
                    }
                } else {
                    if (this.currentSpeed % 10 == 0) {
                        this.currentMultiplier -= 0.1D;
                    }

                    if (this.currentMultiplier <= 0.1D) {
                        this.currentMultiplier = 0.1D;
                    }
                }

                ++this.currentSpeed;
                if (this.currentSpeed == 150) {
                    this.currentSpeed = 0;
                }

                armorStand.setHeadPose(new EulerAngle(0.0D, armorStand.getHeadPose().getY() + this.currentMultiplier, 0.0D));
                if (this.floating) {
                    this.currentY += 0.025D;
                    this.internalY += 0.025D;
                    if (this.internalY >= this.maxY) {
                        this.floating = false;
                    }
                } else {
                    this.currentY -= 0.025D;
                    this.internalY -= 0.025D;
                    if (this.internalY <= this.minY) {
                        this.floating = true;
                    }
                }

                Location after = new Location(location.getWorld(), location.getX(), this.currentY, location.getZ());
                armorStand.teleport(after);

            }
        }).runTaskTimer(HyperRegions.getInstance(), 0L, 0L);
        this.particleTask = (new BukkitRunnable() {
            public void run() {
                particle.sendParticle(Bukkit.getOnlinePlayers(), EnumParticle.FIREWORKS_SPARK, true, armorStand.getLocation().getWorld(), (float) armorStand.getLocation().getX(), (float) armorStand.getLocation().getY(), (float) armorStand.getLocation().getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 1);
            }
        }).runTaskTimer(HyperRegions.getInstance(), 5L, 5L);
    }

    @Override
    public Location getLocation() {
        if(armorStand == null) return null;
        return armorStand.getLocation();
    }
}
