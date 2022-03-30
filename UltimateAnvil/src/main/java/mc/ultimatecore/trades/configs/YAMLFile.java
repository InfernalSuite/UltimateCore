package mc.ultimatecore.trades.configs;

import lombok.Getter;
import mc.ultimatecore.trades.HyperTrades;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class YAMLFile {

    @Getter
    private static YAMLFile instance;

    private FileManager.Config config;

    private final HyperTrades core;

    private final String name;

    public YAMLFile(HyperTrades hyperTrades, String name) {
        instance = this;
        this.name = name;
        this.core = hyperTrades;
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
