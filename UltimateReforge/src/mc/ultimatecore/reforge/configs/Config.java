package mc.ultimatecore.reforge.configs;

import mc.ultimatecore.reforge.HyperReforge;

public class Config extends YAMLFile{
    public String prefix;
    public String anvilFuseSound;

    public Config(HyperReforge hyperSkills, String name) {
        super(hyperSkills, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        //------------------------------------------------//
        prefix = getConfig().get().getString("prefix");
        anvilFuseSound = getConfig().get().getString("reforgeFuseSound");
        //------------------------------------------------//
    }
}
