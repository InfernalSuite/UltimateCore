package mc.ultimatecore.farm.commands;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import mc.ultimatecore.helper.utils.StringUtils;
import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.particle.EnumParticle;
import mc.ultimatecore.farm.particle.Particle;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;


public class ParticleCommand extends Command {

    public ParticleCommand() {
        super(Collections.singletonList("dragon"), "Add a new type of crop", "hyperregions.addtype", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Location location = new Location(Bukkit.getWorld("world"), -238, 108, -377);
        Player player = (Player) sender;
        if(args[1].equalsIgnoreCase("spawn")){
            EnderDragon enderDragon = location.getWorld().spawn(location, EnderDragon.class);
            HyperRegions.getInstance().getDragonManager().setDragon(enderDragon);
        }else if(args[1].equalsIgnoreCase("kill")) {
            HyperRegions.getInstance().getDragonManager().kill();
        }else if(args[1].equalsIgnoreCase("target")) {
            HyperRegions.getInstance().getDragonManager().setTarget(player);
            HyperRegions.getInstance().getDragonManager().setPhase(EnderDragon.Phase.CHARGE_PLAYER);
        }else if(args[1].equalsIgnoreCase("circling")) {
            HyperRegions.getInstance().getDragonManager().setPhase(EnderDragon.Phase.CIRCLING);
        }else if(args[1].equalsIgnoreCase("hover")) {
            HyperRegions.getInstance().getDragonManager().setPhase(EnderDragon.Phase.HOVER);
        }else if(args[1].equalsIgnoreCase("move")) {
            HyperRegions.getInstance().getDragonManager().setAI(true);
        }else if(args[1].equalsIgnoreCase("afk")) {
            HyperRegions.getInstance().getDragonManager().setAI(false);
        }else if(args[1].equalsIgnoreCase("tp")) {
            HyperRegions.getInstance().getDragonManager().setTeleport(!HyperRegions.getInstance().getDragonManager().isTeleport());
        }else if(args[1].equalsIgnoreCase("shoot")) {
            HyperRegions.getInstance().getDragonManager().shoot(player);
        }else if(args[1].equalsIgnoreCase("br")) {
            HyperRegions.getInstance().getDragonManager().setPhase(EnderDragon.Phase.BREATH_ATTACK);
        }

        return false;
    }




    @Override
    public void admin(CommandSender sender, String[] args) {
        execute(sender, args);
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
