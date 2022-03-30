package com.infernalsuite.ultimatecore.pets.configs;

import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.Item;
import com.infernalsuite.ultimatecore.pets.objects.Tier;
import com.infernalsuite.ultimatecore.pets.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Config extends YAMLFile {
    public String prefix = "&e&lPets &7";
    public boolean upAndDownPets = true;
    public String petLevelUPSound = "ENTITY_PLAYER_LEVELUP";
    public String selectedPetName = "&a%pet_name%";
    public String selectedPetNone = "&cNone";
    public Map<Tier, Item> petItems = new HashMap<>();
    public int skillsXP;
    public int blockBreakXP;
    public boolean debug;

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
        skillsXP = (int) getConfig().getDouble("xp.skillsPoints");
        blockBreakXP = (int) getConfig().getDouble("xp.blockBreak");
        debug = getConfig().getBoolean("DEBUG");
        //------------------------------------------------//
    }
}
