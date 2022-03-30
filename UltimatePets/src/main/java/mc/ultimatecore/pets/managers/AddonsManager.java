package mc.ultimatecore.pets.managers;

import lombok.Getter;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.addons.AureliumAPIManager;
import mc.ultimatecore.pets.utils.StringUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class AddonsManager {
    @Getter
    private AureliumAPIManager aurelliumSkills;
    @Getter
    private boolean hyperSkills;
    @Getter
    private Economy economy;
    private final HyperPets plugin;

    public AddonsManager(HyperPets plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        String name = plugin.getDescription().getName();
        if (isPlugin("AureliumSkills")) {
            aurelliumSkills = new AureliumAPIManager();
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into AureliumSkills!"));
        }
        if (isPlugin("Vault")){
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (rsp != null) this.economy = rsp.getProvider();
        }
        hyperSkills = Bukkit.getPluginManager().getPlugin("UltimateCore-Skills") != null;
    }

    private boolean isPlugin(String name){
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }

    public boolean isAurellium(){
        return aurelliumSkills != null;
    }
}