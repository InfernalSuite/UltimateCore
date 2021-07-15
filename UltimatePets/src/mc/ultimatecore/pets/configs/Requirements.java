package mc.ultimatecore.pets.configs;

import lombok.Getter;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.Tier;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Requirements extends YAMLFile {

    private Map<String, Map<Integer, Double>> levelRequirements;

    public Requirements(HyperPets hyperPets, String name, boolean defaults, boolean save) {
        super(hyperPets, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadDefaults();
    }

    private void loadDefaults() {
        levelRequirements = new HashMap<>();
        //------------------------------------------------//
        for(Tier tier : HyperPets.getInstance().getTiers().getTierList().values()){
            ConfigurationSection mapSection = getConfig().getConfigurationSection(tier.getName());
            if(mapSection != null){
                HashMap<Integer, Double> levels = new HashMap<>();
                mapSection.getKeys(false).forEach(level -> levels.put(Integer.valueOf(level), getConfig().getDouble(tier.getName()+"."+level)));
                levelRequirements.put(tier.getName(), levels);
            }
        }
        /*for(String tier : levelRequirements.keySet()){
            HashMap<Integer, Double> slevelRequirements = levelRequirements.get(tier);
            for(Integer integer : slevelRequirements.keySet()){
                Bukkit.getConsoleSender().sendMessage(tier+" "+integer+": "+slevelRequirements.get(integer));
            }
        }*/
        //------------------------------------------------//
    }
}