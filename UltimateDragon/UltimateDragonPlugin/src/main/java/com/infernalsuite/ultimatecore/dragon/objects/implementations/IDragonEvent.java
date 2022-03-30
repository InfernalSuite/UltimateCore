package com.infernalsuite.ultimatecore.dragon.objects.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public abstract class IDragonEvent {
    protected int time = 0;
    protected final int duration;
    protected final int repeat;
    protected BukkitTask task;
    protected final double dragonSpeed;
    protected final boolean freeze;
    @Setter
    protected UCEnderDragon enderDragon;
    public abstract void start();

    public void end(){
        if(task != null) task.cancel();
    }

    public int getRemainingTime(){
        return Math.abs(duration - time);
    }

    protected void checkFreeze(){
        if(!freeze) return;
        Location location = enderDragon.getLocation();
        Location spawn = HyperDragons.getInstance().getDragonManager().getDragonGame().getSpawn();
        Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () ->{
            HyperDragons.getInstance().getNms().setAFK(enderDragon.get());
            if(location != spawn) enderDragon.teleport(spawn);
        });
    }

    protected void move(){
        Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> {
            if(!freeze && enderDragon.stillAlive()) HyperDragons.getInstance().getNms().move(enderDragon.get());
        });
    }

    protected void target(Entity entity){
        Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> {
            if(!freeze && enderDragon.stillAlive()) HyperDragons.getInstance().getNms().setTarget(enderDragon.get(), entity);
        });
    }

    public boolean isActive() {
        return task != null && !task.isCancelled();
    }

}
