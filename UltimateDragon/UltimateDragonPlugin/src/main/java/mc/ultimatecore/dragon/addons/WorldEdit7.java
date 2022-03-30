package mc.ultimatecore.dragon.addons;

import com.sk89q.worldedit.WorldEdit;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.implementations.SchematicImpl;
import mc.ultimatecore.dragon.implementations.WorldEditPluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class WorldEdit7 extends WorldEditPluginImpl {

    public WorldEdit7(String displayName) {
        super(displayName);
    }

    @Override
    public CompletableFuture<Void> pasteSchematic(SchematicImpl schematic, Location location){
        CompletableFuture<Void> future = new CompletableFuture<>();
        Runnable pasteTask = () -> {
            File file = schematic.getSchematicFile();
            com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat format = com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats.findByFile(file);
            try (com.sk89q.worldedit.extent.clipboard.io.ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                com.sk89q.worldedit.extent.clipboard.Clipboard clipboard = reader.read();
                try (com.sk89q.worldedit.EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new com.sk89q.worldedit.bukkit.BukkitWorld(location.getWorld()), -1)) {
                    com.sk89q.worldedit.function.operation.Operation operation = new com.sk89q.worldedit.session.ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(com.sk89q.worldedit.math.BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                            .ignoreAirBlocks(true)
                            .build();
                    com.sk89q.worldedit.function.operation.Operations.complete(operation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
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
