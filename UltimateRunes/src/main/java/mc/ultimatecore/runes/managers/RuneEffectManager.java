package mc.ultimatecore.runes.managers;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import mc.ultimatecore.runes.HyperRunes;
import mc.ultimatecore.runes.enums.RuneEffect;
import mc.ultimatecore.runes.objects.Rune;
import mc.ultimatecore.runes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class RuneEffectManager {

    private static final XMaterial[] RANDOM_ITEMS = {XMaterial.GOLDEN_APPLE, XMaterial.GOLDEN_CARROT, XMaterial.GOLD_INGOT, XMaterial.GOLDEN_CHESTPLATE, XMaterial.GOLD_NUGGET};

    private int taskID;

    private final Rune rune;

    private final Entity entity;

    public RuneEffectManager(Rune rune, Entity entity) {
        this.rune = rune;
        this.entity = entity;
    }

    public void startEffect() {
        makeEffect();
    }

    private void makeEffect(){
        RuneEffect runeEffect = rune.getEffect();
        if(runeEffect.equals(RuneEffect.GOLD)) {
            goldEffect();
        }else if(runeEffect.equals(RuneEffect.LIGHTNING)){
            entity.getWorld().strikeLightningEffect(entity.getLocation());
        }else if(runeEffect.equals(RuneEffect.BLOOD)){
            ParticleDisplay.colored(entity.getLocation(), Color.RED, 2f);
        }else if(runeEffect.equals(RuneEffect.HEARTS) || runeEffect.equals(RuneEffect.MUSIC)) {
            multipleParticles(runeEffect, 4);
        }else{
            multipleParticles(runeEffect, 8);
        }
    }

    public void multipleParticles(RuneEffect runeEffect, int amount) {
        new BukkitRunnable() {
            int b = 0;
            public void run(){
                b++;
                if(b <= amount) {
                    Location lc = entity.getLocation();
                    ParticleDisplay.display(lc, Particle.valueOf(runeEffect.getParticle()));
                }else {
                    cancel();
                }
            }
        }.runTaskTimer(HyperRunes.getInstance() , 0L, 5L);
    }

    private void goldEffect(){
        taskID = new BukkitRunnable() {
            int b = 0;
            public void run(){
                Location location = entity.getLocation().clone();
                if(b <= 5) {
                    if(b == 1) {
                        spawnGoldenItem(location, +1.5f);
                        spawnGoldenItem(location, -1.5f);
                    }
                    ParticleDisplay.colored(entity.getLocation().clone(), Color.YELLOW, 10f);
                    b++;
                }else {
                    Bukkit.getScheduler().cancelTask(taskID);
                }
            }
        }.runTaskTimer(HyperRunes.getInstance() , 0L, 5L).getTaskId();
    }

    public void spawnGoldenItem(Location lc, float in) {
        lc.setX(lc.getX() + in);
        lc.setZ(lc.getZ() + in);
        ArmorStand stand = lc.getWorld().spawn(lc, ArmorStand.class);
        stand.setHelmet(RuneEffectManager.RANDOM_ITEMS[Utils.randomNumber(4)].parseItem());
        stand.isSmall();
        stand.setVisible(false);
        stand.setGravity(true);
        stand.setCollidable(false);
        (new BukkitRunnable() {
            public void run() {
                stand.remove();
            }
        }).runTaskLater(HyperRunes.getInstance(), 25L);
    }

}
