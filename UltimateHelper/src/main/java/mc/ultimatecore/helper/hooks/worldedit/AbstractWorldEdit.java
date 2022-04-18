package mc.ultimatecore.helper.hooks.worldedit;

import org.bukkit.*;

public abstract class AbstractWorldEdit implements WorldEdit {

    @Override
    public boolean isAsync() {
        return Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit") || Bukkit.getPluginManager().isPluginEnabled("AsyncWorldEdit");
    }

}
