package mc.ultimatecore.helper.hooks.worldedit;

import org.bukkit.*;

import java.nio.file.*;

public abstract class AbstractWorldEdit implements WorldEdit {

    @Override
    public SchematicImpl getSchematic(String name, String fileName, Path path) {
        return new SchematicImpl(this, name, fileName, path);
    }
    @Override
    public SchematicImpl getSchematic(String name, Path path) {
        return new SchematicImpl(this, name, path);
    }

    @Override
    public boolean isAsync() {
        return Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit") || Bukkit.getPluginManager().isPluginEnabled("AsyncWorldEdit");
    }

}
