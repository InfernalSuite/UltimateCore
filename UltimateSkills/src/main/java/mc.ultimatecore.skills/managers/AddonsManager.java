package mc.ultimatecore.skills.managers;

import lombok.Getter;
import mc.ultimatecore.helper.regionwrapper.RegionPluginImpl;
import mc.ultimatecore.helper.regionwrapper.WorldGuard;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.addons.*;
import mc.ultimatecore.skills.implementations.EconomyPluginImpl;
import mc.ultimatecore.skills.implementations.ManagerImpl;
import mc.ultimatecore.skills.listener.minions.JetMinionsListener;
import mc.ultimatecore.skills.listener.minions.UltraMinionsListener;
import mc.ultimatecore.skills.listener.mmoitems.AbilityUseEventListener;
import mc.ultimatecore.skills.listener.skills.HyperAnvilListener;
import mc.ultimatecore.skills.listener.skills.HyperCraftingListener;
import mc.ultimatecore.skills.listener.skills.HyperEnchantingListener;
import mc.ultimatecore.skills.listener.skills.HyperRegionListener;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.List;

@Getter
public class AddonsManager extends ManagerImpl {
    
    private MMOItemsAPIManager mmoItems;
    private RegionPluginImpl regionPlugin;
    private MythicMobsAPIManager mythicMobs;
    private EconomyPluginImpl economyPlugin;
    private ClipPlaceholderAPIManager placeholderAPI;
    private MVDWPlaceholderAPIManager mvdwPlaceholderAPI;
    private CitizensAPIManager citizensAPIManager;
    private EcoEnchantsAPIManager ecoEnchants;
    
    public AddonsManager(HyperSkills plugin) {
        super(plugin);
        load();
    }
    
    @Override
    public void load() {
        String name = plugin.getDescription().getName();
        if (isPlugin("UltimateCore-Crafting")) {
            registerListeners(new HyperCraftingListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into HyperCrafting!"));
        }
        if (isPlugin("UltimateCore-Enchantment")) {
            registerListeners(new HyperEnchantingListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into HyperEnchants!"));
        }
        if (isPlugin("UltimateCore-Anvil")) {
            registerListeners(new HyperAnvilListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into HyperAnvil!"));
        }
        if (isPlugin("UltimateCore-Farm")) {
            registerListeners(new HyperRegionListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into HyperRegions!"));
        }
        if (isPlugin("MMOItems") && isPlugin("MythicLib")) {
            mmoItems = new MMOItemsAPIManager("MMOItems & MythicLib");
            registerListeners(new AbilityUseEventListener());
        }
        if (isPlugin("Residence")) {
            regionPlugin = new ResidenceSupportAPIManager("Residence");
        } else if (isPlugin("WorldGuard")) {
            if (isPlugin("WorldEdit") || isPlugin("FastAsyncWorldEdit")) {
                regionPlugin = new WorldGuard();
                Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into WorldEdit && WorldGuard!"));
            }
        } else if (isPlugin("UltraRegions")) {
            regionPlugin = new UltraRegionsAPIManager("UltraRegions");
        }
        if (isPlugin("MythicMobs")) {
            mythicMobs = new MythicMobsAPIManager("MythicMobs");
        }
        if (isPlugin("Vault"))
            economyPlugin = new VaultAPIManager("Vault");
        if (isPlugin("Citizens"))
            citizensAPIManager = new CitizensAPIManager("Citizens");
        if (isPlugin("PlaceholderAPI")) {
            placeholderAPI = new ClipPlaceholderAPIManager(plugin);
            placeholderAPI.register();
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into PlaceholderAPI!"));
        }
        if (isPlugin("MVDWPlaceholderAPI")) {
            mvdwPlaceholderAPI = new MVDWPlaceholderAPIManager(plugin);
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into MVDWPlaceholderAPI!"));
        }
        if (isPlugin("JetsMinions") && plugin.getConfiguration().jetMinions) {
            registerListeners(new JetMinionsListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into JetMinions!"));
        }
        if (isPlugin("UltraMinions") && plugin.getConfiguration().ultraMinions) {
            registerListeners(new UltraMinionsListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into UltraMinions!"));
        }
        if (isPlugin("EcoEnchants"))
            ecoEnchants = new EcoEnchantsAPIManager("EcoEnchants");
    }
    
    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, HyperSkills.getInstance());
    }
    
    private boolean isPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }
    
    private String getPluginVersion(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name).getDescription().getVersion();
    }
    
    private List<String> getPluginAuthor(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name).getDescription().getAuthors();
    }
    
    public boolean isMMOItems() {
        return mmoItems != null;
    }
    
    public boolean isEcoEnchants() {
        return ecoEnchants != null;
    }
    
}