package mc.ultimatecore.souls.configs;

import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;

import java.util.ArrayList;
import java.util.List;

public class Config extends YAMLFile {
    
    public String prefix = "&7[&e&lSouls&7]";
    public String soulTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk2OTIzYWQyNDczMTAwMDdmNmFlNWQzMjZkODQ3YWQ1Mzg2NGNmMTZjMzU2NWExODFkYzhlNmIyMGJlMjM4NyJ9fX0=";
    public String firstSoulFoundSound = "ENTITY_PLAYER_LEVELUP";
    public String soulFoundSound = "ENTITY_EXPERIENCE_ORB_PICKUP";
    public String tiaClaimSound = "ENTITY_FIREWORK_ROCKET_BLAST";
    
    public String mainCommandPerm = "";
    public List<String> allSoulsFound_Reward = new ArrayList<String>() {{
        add("pex user %player% group set king");
        add("eco give %player% 1000");
    }};
    
    public Config(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }
    
    
    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }
    
    private void loadDefaults() {
        prefix = getConfig().getString("prefix");
        soulTexture = getConfig().getString("soulTexture");
        firstSoulFoundSound = getConfig().getString("firstSoulFoundSound");
        soulFoundSound = getConfig().getString("soulFoundSound");
        mainCommandPerm = getConfig().getString("mainCommandPerm");
        allSoulsFound_Reward = getConfig().getStringList("allSoulsFound_Reward");
        tiaClaimSound = getConfig().getString("tiaClaimSound");
    }
    
    
}
