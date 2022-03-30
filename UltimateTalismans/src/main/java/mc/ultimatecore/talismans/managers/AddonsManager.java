package mc.ultimatecore.talismans.managers;

import lombok.Getter;
import mc.ultimatecore.helper.regionwrapper.RegionPluginImpl;
import mc.ultimatecore.helper.regionwrapper.WorldGuard;
import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.addons.ResidenceSupportAPIManager;
import mc.ultimatecore.talismans.addons.UltraRegionsAPIManager;
import mc.ultimatecore.talismans.addons.VaultAPIManager;
import mc.ultimatecore.talismans.implementations.EconomyPluginImpl;
import mc.ultimatecore.talismans.implementations.ManagerImpl;
import mc.ultimatecore.talismans.utils.StringUtils;
import org.bukkit.Bukkit;

@Getter
public class AddonsManager extends ManagerImpl {
    
    private RegionPluginImpl regionPlugin;
    private EconomyPluginImpl economyPlugin;
    
    public AddonsManager(HyperTalismans plugin) {
        super(plugin);
        load();
    }
    
    @Override
    public void load() {
        String name = plugin.getDescription().getName();
        if (isPlugin("WorldGuard")) {
            if (isPlugin("WorldEdit") || isPlugin("FastAsyncWorldEdit")) {
                regionPlugin = new WorldGuard();
                Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into WorldEdit && WorldGuard!"));
            }
        }
        if (regionPlugin == null) {
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &cNo region plugin was found please install it!"));
        } else {
            String regPlugin = "";
            if (regionPlugin instanceof ResidenceSupportAPIManager)
                regPlugin = "Residence";
            else if (regionPlugin instanceof UltraRegionsAPIManager)
                regPlugin = "Ultra Regions";
            else if (regionPlugin instanceof WorldGuard)
                regPlugin = "World Edit & WorldGuard";
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into " + regPlugin + "!"));
        }
        if (isPlugin("Vault"))
            economyPlugin = new VaultAPIManager("Vault");
    }
    
    private String getPluginVersion(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name).getDescription().getVersion();
    }
    
    private boolean isPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }
}