package mc.ultimatecore.pets.configs;

import lombok.Getter;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.Tier;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


public class Tiers extends YAMLFile {
    
    @Getter
    private Map<String, Tier> tierList;
    
    public Tiers(HyperPets hyperPets, String name, boolean defaults, boolean save) {
        super(hyperPets, name, defaults, save);
        loadDefaults();
    }
    
    @Override
    public void reload() {
        super.reload();
        this.loadDefaults();
    }
    
    private void loadDefaults() {
        tierList = new LinkedHashMap<>();
        //------------------------------------------------//
        ConfigurationSection mapSection = getConfig().getConfigurationSection("");
        if (mapSection != null) {
            for (String tierName : mapSection.getKeys(false)) {
                if (!getConfig().getBoolean(tierName + ".enabled")) continue;
                String displayName = getConfig().getString(tierName + ".displayName");
                int tierValue = getConfig().getInt(tierName + ".tierValue");
                Tier tier = new Tier(tierName, displayName, tierValue);
                tierList.put(tierName, tier);
            }
        }
        //------------------------------------------------//
    }
    
    public Tier first() {
        for (Tier tier : tierList.values())
            return tier;
        return null;
    }
    
    public Tier getTier(String name) {
        if (tierList.containsKey(name))
            return tierList.get(name);
        return null;
    }
    
    public Optional<Tier> getNextTier(int currentTier) {
        return tierList.values().stream().filter(tier -> tier.getTierValue() > currentTier).min(Comparator.comparingInt(Tier::getTierValue));
    }
}