package com.infernalsuite.ultimatecore.reforge.configs;

import com.infernalsuite.ultimatecore.reforge.HyperReforge;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class YAMLFile {

    @Getter
    private static YAMLFile instance;

    private FileManager.Config config;

    private final HyperReforge core;

    private final String name;

    public YAMLFile(HyperReforge hyperSkills, String name) {
        instance = this;
        this.name = name;
        this.core = hyperSkills;
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
