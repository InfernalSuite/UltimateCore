package com.infernalsuite.ultimatecore.dragon.implementations;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.concurrent.CompletableFuture;

public abstract class WorldEditPluginImpl extends SoftDependImpl {
    
    public WorldEditPluginImpl(String displayName) {
        super(displayName);
    }
    
    public abstract CompletableFuture<Void> pasteSchematic(SchematicImpl schematic, Location location);
    
    public boolean isAsync() {
        return Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit") || Bukkit.getPluginManager().isPluginEnabled("AsyncWorldEdit");
    }
}