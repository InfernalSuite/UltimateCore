package com.infernalsuite.ultimatecore.enchantment.configs;

import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Commands extends YAMLFile{
    public List<String> commands;

    public Commands(EnchantmentsPlugin enchantmentsPlugin, String name) {
        super(enchantmentsPlugin, name);
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
        YamlConfiguration cf = getConfig().get();
        commands = cf.getStringList("commands");
    }

}