package mc.ultimatecore.alchemy.configs;

import lombok.Getter;
import mc.ultimatecore.alchemy.HyperAlchemy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class YAMLFile {

    @Getter
    private static YAMLFile instance;

    private FileManager.Config config;

    private final HyperAlchemy core;

    private final String name;

    public YAMLFile(HyperAlchemy hyperCrafting, String name) {
        instance = this;
        this.name = name;
        this.core = hyperCrafting;
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
