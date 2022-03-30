package com.infernalsuite.ultimatecore.dragon.objects.implementations;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UCEnderDragon {
    private final EnderDragon enderDragon;

    public boolean stillAlive(){
        return enderDragon != null && !enderDragon.isDead();
    }

    public Location getLocation(){
        return stillAlive() ? enderDragon.getLocation() : null;
    }

    public void teleport(Location location){
        if(stillAlive()) Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> enderDragon.teleport(location));
    }

    public Collection<Player> getNearby(double radius){
        return enderDragon.getWorld().getNearbyEntities(enderDragon.getLocation(), radius, radius, radius).stream()
                .filter(en -> en instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toList());
    }

    public void setName(String name){
        if(enderDragon == null) return;
        enderDragon.setCustomNameVisible(true);
        StringUtils.color(name);
    }

    public double getHealth(){
        return enderDragon.getHealth();
    }

    public void setPhase(EnderDragon.Phase phase){
        if(enderDragon == null) return;
        //HyperDragons.getInstance().getNms().setPhase(enderDragon, phase);
        enderDragon.setPhase(phase);
    }

    public EnderDragon get(){
        return enderDragon;
    }
}
