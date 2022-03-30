package com.infernalsuite.ultimatecore.dragon.addons;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.implementations.SchematicImpl;
import com.infernalsuite.ultimatecore.dragon.implementations.WorldEditPluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class WorldEdit6 extends WorldEditPluginImpl {
    public WorldEdit6(String displayName) {
        super(displayName);
    }


    @Override
    public CompletableFuture<Void> pasteSchematic(SchematicImpl schematic, Location location){
        CompletableFuture<Void> future = new CompletableFuture<>();
        Runnable pasteTask = () -> {
            try {
                Class<?> bukkitWorldClass = Class.forName("com.sk89q.worldedit.bukkit.BukkitWorld");
                Constructor<?> bukkitWorldConstructor = bukkitWorldClass.getConstructor(World.class);
                Class<?> editSessionClass = Class.forName("com.sk89q.worldedit.EditSession");
                Class<?> localWorldClass = Class.forName("com.sk89q.worldedit.LocalWorld");
                Constructor<?> editSessionConstructor = editSessionClass.getConstructor(localWorldClass, int.class);
                Class<?> cuboidClipboardClass = Class.forName("com.sk89q.worldedit.CuboidClipboard");
                Method loadSchematicMethod = cuboidClipboardClass.getMethod("loadSchematic", File.class);
                Class<?> vectorClass = Class.forName("com.sk89q.worldedit.Vector");
                Constructor<?> vectorConstructor = vectorClass.getConstructor(double.class, double.class, double.class);
                Method pasteMethod = cuboidClipboardClass.getMethod("paste", editSessionClass, vectorClass, boolean.class);

                Object editSessionObj = editSessionConstructor.newInstance(bukkitWorldConstructor.newInstance(location.getWorld()), 999999999);
                Object cuboidClipboardObj = loadSchematicMethod.invoke(null, schematic.getSchematicFile());
                Object vectorObj = vectorConstructor.newInstance(location.getX(), location.getY(), location.getZ());

                pasteMethod.invoke(cuboidClipboardObj, editSessionObj, vectorObj, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        if(isAsync()) {
            Bukkit.getScheduler().runTaskAsynchronously(HyperDragons.getInstance(), () -> {
                pasteTask.run();
                future.complete(null);
            });
        } else {
            Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> {
                pasteTask.run();
                future.complete(null);
            });
        }
        return future;
    }


}
