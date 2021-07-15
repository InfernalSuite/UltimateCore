package mc.ultimatecore.talismans.configs;

import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;

public class Config extends YAMLFile {
    public String prefix;
    public boolean debug;

    public Config(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }


    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        //------------------------------------------------//
        prefix = getConfig().getString("prefix");
        debug = getConfig().getBoolean("DEBUG");

        //------------------------------------------------//
    }
}
