package mc.ultimatecore.pets.configs;

import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.Item;
import mc.ultimatecore.pets.objects.Tier;
import mc.ultimatecore.pets.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Config extends YAMLFile {
    public String prefix = "&e&lPets &7";
    public boolean upAndDownPets = true;
    public String petLevelUPSound = "ENTITY_PLAYER_LEVELUP";
    public String selectedPetName = "&a%pet_name%";
    public String selectedPetNone = "&cNone";
    public Map<Tier, Item> petItems = new HashMap<>();
    public double skillsXP;
    public double blockBreakXP;
    public boolean debug;

    public int syncDelay;

    public Config(HyperPets hyperPets, String name, boolean defaults, boolean save) {
        super(hyperPets, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadDefaults();
    }

    private void loadDefaults(){
        //------------------------------------------------//
        prefix = getConfig().getString("prefix");
        upAndDownPets = getConfig().getBoolean("upAndDownPets");
        petLevelUPSound = getConfig().getString("petLevelUPSound");
        selectedPetName = getConfig().getString("selectedPetName");
        selectedPetNone = getConfig().getString("selectedPetNone");
        for(Tier tier : HyperPets.getInstance().getTiers().getTierList().values())
            petItems.put(tier, Utils.getItemFromConfig(getConfig(), "petItems."+tier.getName()));
        skillsXP = getConfig().getDouble("xp.skillsPoints");
        blockBreakXP = getConfig().getDouble("xp.blockBreak");
        debug = getConfig().getBoolean("DEBUG");
        syncDelay = getConfig().getInt("database.syncDelay");
        //------------------------------------------------//
    }
}
