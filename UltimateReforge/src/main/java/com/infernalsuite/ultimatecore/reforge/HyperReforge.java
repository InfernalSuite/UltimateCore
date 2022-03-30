package com.infernalsuite.ultimatecore.reforge;

import com.infernalsuite.ultimatecore.reforge.commands.CommandManager;
import com.infernalsuite.ultimatecore.reforge.configs.*;
import com.infernalsuite.ultimatecore.reforge.listeners.PlayerJoinLeaveListener;
import com.infernalsuite.ultimatecore.reforge.managers.ChancesManager;
import com.infernalsuite.ultimatecore.reforge.managers.ReforgeManager;
import lombok.Getter;
import mc.ultimatecore.reforge.configs.*;
import com.infernalsuite.ultimatecore.reforge.utils.StringUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HyperReforge extends JavaPlugin {
    
    private static HyperReforge instance;
    private Config configuration;
    private Messages messages;
    private Inventories inventories;
    private CommandManager commandManager;
    private FileManager fileManager;
    private ReforgeManager runesManager;
    private ChancesManager chancesManager;
    private Chances chances;
    private boolean hyperEnchants;
    private Economy economy;
    
    public static HyperReforge getInstance() {
        return instance;
    }
    
    @Override
    public void onEnable() {
        instance = this;
        this.fileManager = new FileManager(this);
        this.chancesManager = new ChancesManager();
        loadConfigs();
        this.commandManager = new CommandManager();
        this.runesManager = new ReforgeManager();
        this.hyperEnchants = Bukkit.getPluginManager().getPlugin("UltimateCore-Enchantment") != null;
        registerListeners(new PlayerJoinLeaveListener());
        setupEconomy();
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + getDescription().getName() + "] Has been enabled!"));
    }
    
    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " Disabled!");
    }
    
    public void sendErrorMessage(Exception e) {
        e.printStackTrace();
    }
    
    public void registerListeners(Listener... listener) {
        for (Listener l : listener) Bukkit.getPluginManager().registerEvents(l, this);
    }
    
    public void loadConfigs() {
        configuration = new Config(this, "config");
        chances = new Chances(this, "chances");
        messages = new Messages(this, "messages");
        inventories = new Inventories(this, "inventories");
        configuration.enable();
        chances.enable();
        messages.enable();
        inventories.enable();
    }
    
    public void reloadConfigs() {
        configuration.reload();
        chances.reload();
        messages.reload();
        inventories.reload();
    }
    
    
    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return;
        economy = rsp.getProvider();
    }
}
