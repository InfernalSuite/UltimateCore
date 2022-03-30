package com.infernalsuite.ultimatecore.dragon.managers;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.addons.MythicMobsAddon;
import com.infernalsuite.ultimatecore.dragon.addons.WorldEdit6;
import com.infernalsuite.ultimatecore.dragon.addons.WorldEdit7;
import com.infernalsuite.ultimatecore.dragon.implementations.CoreManagerImpl;
import com.infernalsuite.ultimatecore.dragon.implementations.WorldEditPluginImpl;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;

@Getter
public class AddonsManager extends CoreManagerImpl {
    private WorldEditPluginImpl worldEdit;
    private MythicMobsAddon mythicMobs;

    public AddonsManager(HyperDragons plugin) {
        super(plugin);
        load();
    }

    @Override
    public void load() {
        if(isPlugin("WorldEdit") || isPlugin("FastAsyncWorldEdit") || isPlugin("AsyncWorldEdit"))
            worldEdit = XMaterial.getVersion() > 13 ? new WorldEdit7("WorldEdit") : new WorldEdit6("WorldEdit");
        else
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[UltimateCore-Dragon] &cWaning WorldEdit is not installed!"));
        if(isPlugin("MythicMobs"))
            mythicMobs = new MythicMobsAddon();
    }


    private boolean isPlugin(String name){
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }

    private String getPluginVersion(String name){
        return Bukkit.getServer().getPluginManager().getPlugin(name).getDescription().getVersion();
    }

    public boolean isMythicMobs(){
        return mythicMobs != null;
    }
}
