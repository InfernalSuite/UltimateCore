package mc.ultimatecore.helper.hooks.worldedit;

import org.bukkit.*;
import org.bukkit.plugin.*;

import java.util.concurrent.*;

public interface WorldEdit {

    CompletableFuture<Void> pasteSchematic(Plugin plugin, Schematic schematic, Location location);

    /**
     * Checks if FAWE or AWE is installed.
     * @return {@code true} if FAWE or AWE is detected
     */
    boolean isAsync();

}
