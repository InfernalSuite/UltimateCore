package com.infernalsuite.ultimatecore.farm.managers;

import lombok.Getter;
import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.farm.hooks.ResidenceAPIManager;
import com.infernalsuite.ultimatecore.farm.hooks.UltraRegionsAPIManager;
import com.infernalsuite.ultimatecore.farm.utils.Utils;
import com.infernalsuite.ultimatecore.helper.regionwrapper.RegionPluginImpl;
import com.infernalsuite.ultimatecore.helper.regionwrapper.WorldGuard;
import org.bukkit.Bukkit;

public class AddonsManager {
    
    private final HyperRegions plugin;
    @Getter
    private RegionPluginImpl regionPlugin;
    
    public AddonsManager(HyperRegions plugin) {
        this.plugin = plugin;
        load();
    }
    
    public void load() {
        String name = plugin.getDescription().getName();
        if (isPlugin("Residence")) {
            regionPlugin = new ResidenceAPIManager();
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e[" + name + "] &aSuccessfully hooked into Residence!"));
        } else if (isPlugin("WorldGuard")) {
            if (isPlugin("WorldEdit") || isPlugin("FastAsyncWorldEdit") || isPlugin("AsyncWorldEdit")) {
                regionPlugin = new WorldGuard();
                Bukkit.getConsoleSender().sendMessage(Utils.color("&e[" + name + "] &aSuccessfully hooked into WorldEdit!"));
            }
        } else if (isPlugin("UltraRegions")) {
            regionPlugin = new UltraRegionsAPIManager();
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e[" + name + "] &aSuccessfully hooked into UltraRegions!"));
        } else {
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e[" + name + "] &cWaning Regions Plugin wasn't found please install it!"));
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }
    
    private boolean isPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }
    
    private String getPluginVersion(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name).getDescription().getVersion();
    }
    
}
