package mc.ultimatecore.helper.v1_15_R1.hooks.worldedit;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.extent.clipboard.*;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.*;
import com.sk89q.worldedit.math.*;
import com.sk89q.worldedit.session.*;
import com.sk89q.worldedit.world.World;
import mc.ultimatecore.helper.hooks.worldedit.AbstractWorldEdit;
import mc.ultimatecore.helper.hooks.worldedit.Schematic;
import org.bukkit.*;
import org.bukkit.plugin.*;

import java.io.*;
import java.util.concurrent.*;

public class WorldEditImpl extends AbstractWorldEdit {

    @Override
    public CompletableFuture<Void> pasteSchematic(Plugin plugin, Schematic schematic, Location location) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Runnable pasteTask = () -> {
            try {
                World adaptedWorld = BukkitAdapter.adapt(location.getWorld());
                EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);

                ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(schematic.getSchematicFile());
                if(clipboardFormat != null) {
                    ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(schematic.getSchematicFile()));
                    Clipboard clipboard = clipboardReader.read();

                    Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(true).build();

                    Operations.complete(operation);
                    editSession.close();
                }
            } catch (IOException | WorldEditException e) {
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
