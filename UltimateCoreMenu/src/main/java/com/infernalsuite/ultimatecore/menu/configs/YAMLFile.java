package com.infernalsuite.ultimatecore.menu.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.menu.HyperCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class YAMLFile {

    @Getter
    private static YAMLFile instance;

    private FileManager.Config config;

    private final HyperCore core;

    private final String name;

    public YAMLFile(HyperCore hyperCore, String name) {
        instance = this;
        this.name = name;
        this.core = hyperCore;
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
