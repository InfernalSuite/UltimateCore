package mc.ultimatecore.helper.hooks.worldedit;

import lombok.*;
import org.bukkit.*;
import org.bukkit.plugin.*;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

@Getter
public class SchematicImpl implements Schematic {

    private final WorldEdit worldEdit;
    private final String name;
    private final Path path;

    SchematicImpl(WorldEdit worldEdit, String name, String fileName, Path path) {
        this.worldEdit = worldEdit;
        this.name = name;
        this.path = path.resolve(fileName);
    }
    SchematicImpl(WorldEdit worldEdit, String name, Path path) {
        this.worldEdit = worldEdit;
        this.name = name;
        this.path = path;
    }

    @Override
    public String getFileName() {
        return path.getFileName().toString();
    }

    @Override
    public File getSchematicFile() {
        return path.toFile();
    }

    public CompletableFuture<Void> pasteSchematic(Plugin plugin, Location location) {
        return worldEdit.pasteSchematic(plugin, this, location);
    }
}
