package mc.ultimatecore.pets;

import lombok.*;
import mc.ultimatecore.helper.*;
import mc.ultimatecore.helper.database.*;
import mc.ultimatecore.pets.api.*;
import mc.ultimatecore.pets.armorequipevent.*;
import mc.ultimatecore.pets.commands.*;
import mc.ultimatecore.pets.configs.*;
import mc.ultimatecore.pets.database.*;
import mc.ultimatecore.pets.database.implementations.*;
import mc.ultimatecore.pets.listeners.*;
import mc.ultimatecore.pets.listeners.pets.*;
import mc.ultimatecore.pets.managers.*;
import mc.ultimatecore.pets.nms.*;
import mc.ultimatecore.pets.objects.*;
import mc.ultimatecore.pets.playerdata.*;
import mc.ultimatecore.pets.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

import java.util.*;

@Getter
public class HyperPets extends UltimatePlugin {

    private static HyperPets instance;
    private Config configuration;
    private Messages messages;
    private Inventories inventories;
    private Levels levels;
    private NMS nms;
    private Pets pets;
    private Tiers tiers;
    private CommandManager commandManager;
    private Database pluginDatabase;
    private PetsManager petsManager;
    private UserManager userManager;
    private PetsLeveller petsLeveller;
    private HyperPetsAPI api;
    private AddonsManager addonsManager;

    @Override
    public void onEnable() {
        instance = this;

        loadNms();

        addonsManager = new AddonsManager(this);

        loadConfigs();

        commandManager = new CommandManager("pets");

        petsLeveller = new PetsLeveller(this);

        api = new HyperPetsAPIImpl(this);

        Credentials credentials = Credentials.fromConfig(configuration.getConfig());

        pluginDatabase = credentials.getDatabaseType() == DatabaseType.MySQL ? new MySQLDatabase(this, credentials) : new SQLiteDatabase(this, credentials);

//        try {
//            pluginDatabase.createTablesAsync().get();
//        } catch (InterruptedException | ExecutionException e) {
//            Bukkit.shutdown();
//            return;
//        }


//        petsManager = new PetsManager(this);
//        userManager = new UserManager(this);

        pluginDatabase.createTablesAsync().thenRun(() -> {
            petsManager = new PetsManager(this);
            userManager = new UserManager(this);

            registerListeners(new InventoryClickListener(), addonsManager.isHyperSkills() ? new SkillsHookListener(this) : new BlockBreakListener(this), new ArmorListener(new ArrayList<>()), new PetEquipListener(), new PetClickListener(), new PlayerJoinLeaveListener(this), new PetRegisterListener(), new TeleportListener(userManager));

            Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " Has been enabled!"));
        });
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.closeInventory();
            User user = userManager.getUser(p);
            if (user.getPlayerPet() != null)
                user.getPlayerPet().removePet(true);
        }
        if (userManager != null) userManager.disable();
        if (petsManager != null) petsManager.disable();
        getLogger().info(getDescription().getName() + " Disabled!");
    }

    public void loadNms() {
        try {
            nms = (NMS) Class.forName("mc.ultimatecore.pets.nms." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]).newInstance();
        } catch (ClassNotFoundException e) {
            getLogger().warning("Unsupported Version Detected: " + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
            Bukkit.getPluginManager().disablePlugin(this);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void sendErrorMessage(Exception e) {
        e.printStackTrace();
    }

    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, this);
    }


    public void loadConfigs() {
        levels = new Levels(this, "levels", false, false);
        tiers = new Tiers(this, "tiers", false, false);
        messages = new Messages(this, "messages", true, false);
        configuration = new Config(this, "config", true, false);
        inventories = new Inventories(this, "inventories", true, false);
        pets = new Pets(this, "pets", false, false);
    }

    public void reloadConfigs() {
        levels.reload();
        tiers.reload();
        messages.reload();
        configuration.reload();
        inventories.reload();
        pets.reload();
    }

    public static HyperPets getInstance() {
        return instance;
    }

    public void sendDebug(String message, DebugType debugType) {
        if (!configuration.debug) return;
        if (debugType == DebugType.LOG)
            getLogger().info(message);
        else
            Bukkit.getConsoleSender().sendMessage(Utils.color(message));
    }

    @Override
    public String getPluginName() {
        return getDescription().getName();
    }
}
