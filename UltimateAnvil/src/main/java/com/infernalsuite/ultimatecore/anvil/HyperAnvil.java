package com.infernalsuite.ultimatecore.anvil;

import lombok.Getter;
import com.infernalsuite.ultimatecore.anvil.commands.CommandManager;
import com.infernalsuite.ultimatecore.anvil.configs.Config;
import com.infernalsuite.ultimatecore.anvil.configs.FileManager;
import com.infernalsuite.ultimatecore.anvil.configs.Inventories;
import com.infernalsuite.ultimatecore.anvil.configs.Messages;
import com.infernalsuite.ultimatecore.anvil.listeners.AnvilListener;
import com.infernalsuite.ultimatecore.anvil.listeners.AureliumSkillsListener;
import com.infernalsuite.ultimatecore.anvil.listeners.PlayerJoinLeaveListener;
import com.infernalsuite.ultimatecore.anvil.managers.UsersManager;
import com.infernalsuite.ultimatecore.anvil.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HyperAnvil extends JavaPlugin {
    private static HyperAnvil instance;
    private Config configuration;
    private Messages messages;
    private Inventories inventories;
    private CommandManager commandManager;
    private FileManager fileManager;
    private UsersManager runesManager;
    private boolean hyperEnchants;

    public static HyperAnvil getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        loadConfigs();
        commandManager = new CommandManager("hyperanvil");

        this.runesManager = new UsersManager();
        hyperEnchants = Bukkit.getPluginManager().getPlugin("UltimateCore-Enchantment") != null;

        registerListeners(new AnvilListener(), new PlayerJoinLeaveListener());

        hookAurelium();

        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + getDescription().getName() + "] Has been enabled!"));

    }

    @Override
    public void onDisable() {
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
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperAnvil] &aSuccessfully hooked with AureliumSkills!"));
            registerListeners(new AureliumSkillsListener(this));
        }
    }

    public void loadConfigs() {
        configuration = new Config(this, "config");
        messages = new Messages(this, "messages");
        inventories = new Inventories(this, "inventories");
        configuration.enable();
        messages.enable();
        inventories.enable();
    }

    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        inventories.reload();
    }
}
