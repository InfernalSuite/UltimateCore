package mc.ultimatecore.dragon.managers;

import lombok.*;
import mc.ultimatecore.dragon.*;
import mc.ultimatecore.dragon.addons.*;
import mc.ultimatecore.dragon.implementations.*;
import mc.ultimatecore.dragon.utils.*;
import mc.ultimatecore.helper.hooks.worldedit.*;
import org.bukkit.*;

@Getter
public class AddonsManager extends CoreManagerImpl {
    private WorldEdit worldEdit;
    private MythicMobsAddon mythicMobs;

    public AddonsManager(HyperDragons plugin) {
        super(plugin);
        load();
    }

    @Override
    public void load() {
        if(isPlugin("WorldEdit") || isPlugin("FastAsyncWorldEdit") || isPlugin("AsyncWorldEdit"))
            worldEdit = HyperDragons.getInstance().getVersionHook().getWorldEdit();
        else
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[UltimateCore-Dragon] &cWarning WorldEdit is not installed!"));
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
