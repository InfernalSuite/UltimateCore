package mc.ultimatecore.crafting;

import lombok.Getter;
import mc.ultimatecore.crafting.commands.CommandManager;
import mc.ultimatecore.crafting.configs.*;
import mc.ultimatecore.crafting.listeners.*;
import mc.ultimatecore.crafting.managers.PlayerManager;
import mc.ultimatecore.crafting.nms.*;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.*;
import java.util.*;

public class HyperCrafting extends JavaPlugin {

    private static HyperCrafting INSTANCE;
    @Getter
    private Config configuration;
    @Getter
    private Messages messages;
    @Getter
    private PlayerManager playerManager;
    @Getter
    private CommandManager commandManager;
    @Getter
    private Inventories inventories;
    @Getter
    private CraftingRecipes craftingRecipes;
    @Getter
    private BlackList blackList;
    @Getter
    private Categories categories;
    @Getter
    private Commands commands;

    private VanillaCraftingSource nms;

    @Getter
    private final List<Integer> craftingSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);

    @Override
    public void onEnable() {
        this.loadConfigs();
        this.commandManager = new CommandManager("hypercrafting", this);
        registerListeners(new CommandListener(this), new InventoryClickListener(this), new CraftingTableListener(this), new PlayerJoinLeaveListener(this), new InventoryCloseListener(this), new InventoryDragListener());
        this.playerManager = new PlayerManager(this);
        this.nms = setupNMS();
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " Has been enabled!"));
    }

    @Override
    public void onDisable() {
        if (craftingRecipes != null) {
            craftingRecipes.save();
            this.craftingRecipes = null;
        }
        this.playerManager.purgeUsers();
        this.playerManager = null;
        for (Player p : Bukkit.getOnlinePlayers())
            p.closeInventory();
        getLogger().info(getDescription().getName() + " Disabled!");
    }


    private VanillaCraftingSource setupNMS() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return (VanillaCraftingSource) Class.forName("mc.ultimatecore.crafting.nms." + version).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            getLogger().warning("Unsupported Version Detected: " + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
        }

        return null;
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

    public VanillaCraftingSource getVanillaSource() {
        return nms;
    }
}
