package com.infernalsuite.ultimatecore.enchantment.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class YAMLFile {

    @Getter
    private static YAMLFile instance;

    private FileManager.Config config;

    private final EnchantmentsPlugin core;

    private final String name;

    public YAMLFile(EnchantmentsPlugin enchantmentsPlugin, String name) {
        instance = this;
        this.name = name;
        this.core = enchantmentsPlugin;
    }

    public void reload() {
        this.config.reload();
    }

    public void enable() {
        this.config = this.core.getFileManager().getConfig(name+".yml").copyDefaults(true).save();
    }

    public void disable() {
        for (Player p : Bukkit.getServer().getOnlinePlayers())
            p.closeInventory();
    }


}
