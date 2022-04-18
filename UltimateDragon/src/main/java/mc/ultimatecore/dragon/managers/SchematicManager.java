package mc.ultimatecore.dragon.managers;

import lombok.Getter;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.implementations.CoreManagerImpl;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.helper.hooks.worldedit.*;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class SchematicManager extends CoreManagerImpl {

    @Getter
    private final List<SchematicImpl> schematics;

    private final File schematicsFolder;

    public SchematicManager(HyperDragons plugin){
        super(plugin);
        this.schematics = new ArrayList<>();
        this.schematicsFolder = new File(plugin.getDataFolder()+"/schematics", "");
        schematicsFolder.mkdirs();
        load();
    }

    @Override
    public void load() {
        Arrays.stream(schematicsFolder.listFiles()).iterator().forEachRemaining(this::loadSchematic);

        if(!getSchematic(HyperDragons.getInstance().getConfiguration().schematic).isPresent())
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&c[UltimateCore-Dragon] Schematic "+HyperDragons.getInstance().getConfiguration().schematic)+" wasn't found!");
    }


    public void loadSchematic(File file){
        String fileName = file.getName();
        if(fileName.contains(".schematic") || fileName.contains(".schem")){
            String name = fileName.replace(".schematic", "").replace(".schem", "");
            SchematicImpl worldEditSchematic = plugin.getAddonsManager().getWorldEdit().getSchematic(name, file.toPath());
            schematics.add(worldEditSchematic);
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[UltimateCore-Dragon] &aSuccessfully loaded WorldEdit Schematic "+name+"!"));
        }
    }

    public Optional<SchematicImpl> getSchematic(String name){
        return schematics.stream().filter(schematic -> schematic.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void addSchematic(SchematicImpl schematic){
        schematics.add(schematic);
    }
}
