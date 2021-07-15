package mc.ultimatecore.dragon.managers;

import lombok.Getter;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.implementations.CoreManagerImpl;
import mc.ultimatecore.dragon.implementations.SchematicImpl;
import mc.ultimatecore.dragon.objects.others.WorldEditSchematic;
import mc.ultimatecore.dragon.utils.StringUtils;
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
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&c[HyperDragon] Schematic "+HyperDragons.getInstance().getConfiguration().schematic)+" wasn't found!");
    }


    public void loadSchematic(File file){
        String fileName = file.getName();
        if(fileName.contains(".schematic") || fileName.contains(".schem")){
            String name = fileName.replace(".schematic", "").replace(".schem", "");
            WorldEditSchematic worldEditSchematic = new WorldEditSchematic(name, fileName);
            schematics.add(worldEditSchematic);
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperDragon] &aSuccessfully loaded WorldEdit Schematic "+name+"!"));
        }
    }

    public Optional<SchematicImpl> getSchematic(String name){
        return schematics.stream().filter(schematic -> schematic.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void addSchematic(SchematicImpl schematic){
        schematics.add(schematic);
    }
}
