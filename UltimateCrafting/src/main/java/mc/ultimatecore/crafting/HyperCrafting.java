package mc.ultimatecore.crafting;

import lombok.Getter;
import mc.ultimatecore.crafting.commands.CommandManager;
import mc.ultimatecore.crafting.configs.*;
import mc.ultimatecore.crafting.listeners.CommandListener;
import mc.ultimatecore.crafting.listeners.CraftingTableListener;
import mc.ultimatecore.crafting.listeners.InventoryClickListener;
import mc.ultimatecore.crafting.listeners.PlayerJoinLeaveListener;
import mc.ultimatecore.crafting.managers.PlayersDataManager;
import mc.ultimatecore.crafting.nms.*;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperCrafting extends JavaPlugin {

    private static HyperCrafting instance;
    @Getter
    private Config configuration;
    @Getter
    private Messages messages;
    @Getter
    private PlayersDataManager playersData;
    @Getter
    private CommandManager commandManager;
    @Getter
    private Inventories inventories;
    @Getter
    private CraftingRecipes craftingRecipes;
    @Getter
    private NMS nms;
    @Getter
    private BlackList blackList;
    @Getter
    private Categories categories;
    @Getter
    private Commands commands;

    public static HyperCrafting getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        this.nms = setupNMS();
        if (this.nms == null) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;

        loadConfigs();

        commandManager = new CommandManager("hypercrafting");

        registerListeners(new CommandListener(this), new InventoryClickListener(), new CraftingTableListener(this), new PlayerJoinLeaveListener(this));

        playersData = new PlayersDataManager();

        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " Has been enabled!"));
    }


    private NMS setupNMS() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return (NMS) Class.forName("mc.ultimatecore.crafting.nms." + version).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            getLogger().warning("Un-Supported Minecraft Version: " + version);
        }
        return null;
    }

    @Override
    public void onDisable() {
        if (craftingRecipes != null)
            craftingRecipes.save();
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

    public void loadConfigs() {
        configuration = new Config(this, "config", true);
        messages = new Messages(this, "messages", true);
        inventories = new Inventories(this, "inventories", true);
        craftingRecipes = new CraftingRecipes(this, "recipes", false);
        blackList = new BlackList(this, "blacklist", false);
        categories = new Categories(this, "categories", false);
        commands = new Commands(this, "commands", false);
    }

    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        inventories.reload();
        craftingRecipes.reload();
        blackList.reload();
        categories.reload();
        commands.reload();
    }
}
