package com.infernalsuite.ultimatecore.dragon.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.structures.DragonAltar;
import com.infernalsuite.ultimatecore.dragon.objects.structures.DragonStructure;
import com.infernalsuite.ultimatecore.dragon.utils.Utils;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

@Getter
public class Structures extends YAMLFile {

    private DragonStructure dragonStructure;

    public Structures(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    public void save(){
        DragonStructure structure = HyperDragons.getInstance().getDragonManager().getDragonStructure();
        getConfig().set("crystals", null);
        getConfig().set("altars", null);
        getConfig().set("spawn", null);
        super.save();
        int i = 0;
        for(DragonAltar altar : structure.getAltars()){
            Utils.saveLocationToConfig(getConfig(), "altars."+i, altar);
            i++;
        }
        i = 0;
        for(Location location : structure.getCrystals()){
            Utils.saveLocationToConfig(getConfig(), "crystals."+i, location);
            i++;
        }
        Utils.saveLocationToConfig(getConfig(), "spawn", structure.getSpawnLocation());
        super.save();
    }

    private void loadDefaults(){
        dragonStructure = new DragonStructure();
        YamlConfiguration cf = getConfig();
        try {
            ConfigurationSection configurationSection = cf.getConfigurationSection("crystals");
            if(configurationSection != null)
                for(String id : configurationSection.getKeys(false))
                    dragonStructure.getCrystals().add(Utils.getLocationFromConfig(getConfig(), "crystals."+id));
            configurationSection = getConfig().getConfigurationSection("altars");
            if(configurationSection != null)
                for(String id : configurationSection.getKeys(false))
                    dragonStructure.addAltar(Utils.getAltarFromConfig(getConfig(), "altars."+id));
            if(cf.contains("spawn"))
                dragonStructure.setSpawnLocation(Utils.getLocationFromConfig(getConfig(), "spawn"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
