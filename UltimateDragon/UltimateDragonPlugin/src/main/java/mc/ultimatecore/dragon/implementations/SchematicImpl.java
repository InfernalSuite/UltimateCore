package mc.ultimatecore.dragon.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Getter
public abstract class SchematicImpl {
    protected final String name;
    protected final String fileName;
    public abstract File getSchematicFile();
    public abstract CompletableFuture<Void> pasteSchematic(Location location);
}
