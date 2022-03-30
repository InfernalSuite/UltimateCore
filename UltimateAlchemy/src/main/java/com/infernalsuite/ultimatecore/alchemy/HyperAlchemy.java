package com.infernalsuite.ultimatecore.alchemy;

import lombok.Getter;
import com.infernalsuite.ultimatecore.alchemy.commands.CommandManager;
import mc.ultimatecore.alchemy.configs.*;
import com.infernalsuite.ultimatecore.alchemy.listeners.AureliumSkillsListener;
import com.infernalsuite.ultimatecore.alchemy.listeners.BrewingStandListener;
import com.infernalsuite.ultimatecore.alchemy.listeners.InventoryClickListener;
import com.infernalsuite.ultimatecore.alchemy.managers.BrewingStandManager;
import com.infernalsuite.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HyperAlchemy extends JavaPlugin {
    private static HyperAlchemy instance;
    private Config configuration;
    private Messages messages;
    private CommandManager commandManager;
    private Inventories inventories;
    private BrewingRecipes brewingRecipes;
    private FileManager fileManager;
    private BrewingStandManager brewingStandManager;

    public static HyperAlchemy getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        fileManager = new FileManager(this);

        loadConfigs();

        commandManager = new CommandManager("hyperalchemy");

        registerListeners(new BrewingStandListener(), new InventoryClickListener());

        hookAurelium();

        brewingStandManager = new BrewingStandManager();

        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e"+getDescription().getName()+" Has been enabled!"));
    }


    @Override
    public void onDisable() {
        if(brewingRecipes != null)
            brewingRecipes.save();
        for (Player p : Bukkit.getOnlinePlayers())
            p.closeInventory();
        getLogger().info(getDescription().getName() + " Disabled!");
    }


    public void sendErrorMessage(Exception e) {
        e.printStackTrace();
    }

    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, this);
    }

    private void hookAurelium(){
        if(Bukkit.getPluginManager().getPlugin("AureliumSkills") != null){
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperAlchemy] &aSuccessfully hooked with AureliumSkills!"));
            registerListeners(new AureliumSkillsListener(this));
        }
    }

    private void loadConfigs() {
        configuration = new Config(this, "config");
        messages =  new Messages(this, "messages");
        inventories = new Inventories(this, "inventories");
        brewingRecipes = new BrewingRecipes(this, "recipes");

        configuration.enable();
        messages.enable();
        inventories.enable();
        brewingRecipes.enable();

    }

    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        inventories.reload();
        brewingRecipes.reload();

    }
}
