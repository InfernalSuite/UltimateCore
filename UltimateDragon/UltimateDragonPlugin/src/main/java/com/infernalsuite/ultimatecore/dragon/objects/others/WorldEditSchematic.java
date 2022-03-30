package com.infernalsuite.ultimatecore.dragon.objects.others;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.implementations.SchematicImpl;
import com.infernalsuite.ultimatecore.dragon.implementations.WorldEditPluginImpl;
import org.bukkit.Location;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class WorldEditSchematic extends SchematicImpl {

    public WorldEditSchematic(String name, String fileName) {
        super(name, fileName);
    }

    @Override
    public File getSchematicFile() {
        return new File(HyperDragons.getInstance().getDataFolder() + "/schematics", this.getFileName());
    }

    @Override
    public CompletableFuture<Void> pasteSchematic(Location location) {
        WorldEditPluginImpl worldEditPlugin = HyperDragons.getInstance().getAddonsManager().getWorldEdit();
        if(worldEditPlugin != null)
            return worldEditPlugin.pasteSchematic(this, location);
        return null;
    }
}
