package mc.ultimatecore.helper.v1_8_R3.hooks.worldedit;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.world.*;
import mc.ultimatecore.helper.hooks.worldedit.*;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.plugin.*;

import java.io.*;
import java.util.concurrent.*;

public class WorldEditImpl extends AbstractWorldEdit {

    @Override
    public CompletableFuture<Void> pasteSchematic(Plugin plugin, Schematic schematic, Location location) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Runnable pasteTask = () -> {
            try {
                BukkitWorld bukkitWorld = new BukkitWorld(location.getWorld());
                EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(bukkitWorld, 999999999);
                CuboidClipboard cuboidClipboard = CuboidClipboard.loadSchematic(schematic.getSchematicFile());
                Vector vector = new Vector(location.getX(), location.getY(), location.getZ());
                cuboidClipboard.paste(editSession, vector, true);
            } catch (IOException | DataException | MaxChangedBlocksException e) {
                e.printStackTrace();
            }
        };
        if (isAsync()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                pasteTask.run();
                future.complete(null);
            });
        } else {
            Bukkit.getScheduler().runTask(plugin, () -> {
                pasteTask.run();
                future.complete(null);
            });
        }
        return future;
    }

}
