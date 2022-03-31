package mc.ultimatecore.crafting.configs;

import mc.ultimatecore.crafting.HyperCrafting;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Commands extends YAMLFile{
    public List<String> commands;

    public Commands(HyperCrafting hyperCrafting, String name, boolean loadDefaults) {
        super(hyperCrafting, name, loadDefaults);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        YamlConfiguration cf = getConfig();
        commands = cf.getStringList("commands");
    }

}