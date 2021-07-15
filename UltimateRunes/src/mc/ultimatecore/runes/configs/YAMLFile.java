package mc.ultimatecore.runes.configs;

import lombok.Getter;
import mc.ultimatecore.runes.HyperRunes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class YAMLFile {

    @Getter
    private static YAMLFile instance;

    private mc.ultimatecore.runes.configs.FileManager.Config config;

    private final HyperRunes core;

    private final String name;

    public YAMLFile(HyperRunes hyperSkills, String name) {
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
