package mc.ultimatecore.crafting.configs;

import mc.ultimatecore.crafting.HyperCrafting;

public class Config extends YAMLFile{

    public String prefix = "&e&lUltimateCore-Crafting &7";
    public String mainCommandPerm = "";
    public boolean setAsDefaultCraftingTable = true;
    public boolean showAvailableRecipes = true;

    public Config(HyperCrafting hyperCrafting, String name, boolean loadDefaults) {
        super(hyperCrafting, name, loadDefaults);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }


    private void loadDefaults(){
        prefix = getConfig().getString("prefix", "&e&lUltimateCore-Crafting &7");
        mainCommandPerm = getConfig().getString("mainCommandPerm", "");
        setAsDefaultCraftingTable = getConfig().getBoolean("setAsDefaultCraftingTable");
        showAvailableRecipes = getConfig().getBoolean("showAvailableRecipes");
    }
}
