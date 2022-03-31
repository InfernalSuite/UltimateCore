package mc.ultimatecore.farm;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import mc.ultimatecore.farm.commands.CommandManager;
import mc.ultimatecore.farm.configs.Config;
import mc.ultimatecore.farm.configs.Guardians;
import mc.ultimatecore.farm.configs.Inventories;
import mc.ultimatecore.farm.configs.Messages;
import mc.ultimatecore.farm.guardians.Guardian;
import mc.ultimatecore.farm.listeners.CropBreakListener;
import mc.ultimatecore.farm.listeners.InventoryClickListener;
import mc.ultimatecore.farm.managers.AddonsManager;
import mc.ultimatecore.farm.managers.RegionsManager;
import mc.ultimatecore.farm.nms.*;
import mc.ultimatecore.farm.utils.Utils;
import mc.ultimatecore.helper.UltimatePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;

import java.util.Optional;

@Getter
public class HyperRegions extends UltimatePlugin {

    /*
     * This block prevents the Maven Shade plugin to remove the specified classes
     */
    static {
        @SuppressWarnings("unused") Class<?>[] classes = new Class<?>[]{
                v1_8_R3.class,
                v1_12_R1.class,
                v1_14_R1.class,
                v1_15_R1.class,
                v1_16_R3.class,
                v1_17_R1.class,
                v1_18_R1.class
        };
    }

    private static HyperRegions instance;
    private Config configuration;
    private Messages messages;
    private Inventories inventories;
    private AddonsManager addonsManager;
    private RegionsManager farmManager;
    private CommandManager commandManager;
    private Guardians guardians;
    private NMS nms;

    public static HyperRegions getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        loadNMS();

        if (nms == null) {
            setEnabled(false);
            return;
        }

        addonsManager = new AddonsManager(this);

        loadConfigs();

        registerListeners(new InventoryClickListener(), new CropBreakListener(this));

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), this::saveConfigs, 1000L, 1900L);

        commandManager = new CommandManager("ultimatefarm");

        commandManager.registerCommands();

        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " Has been enabled!"));


    }

    private void loadNMS() {
        try {
            nms = (NMS) Class.forName("mc.ultimatecore.farm.nms." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            getLogger().info("Unsupported Version Detected: " + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
        }
    }

    @Override
    public void onDisable() {
        Optional.ofNullable(guardians).ifPresent(guardians1 -> guardians.getGuardians().forEach(Guardian::remove));

        Optional.ofNullable(farmManager).ifPresent(manager -> manager.blockRegenCache.forEach(blockRegen -> blockRegen.disableRegen(true)));

        saveConfigs();

        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);

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
        guardians = new Guardians(this, "guardians", false);
        farmManager = XMaterial.isNewVersion() ? new RegionsManager(this, "regionsManager", false) : new RegionsManager(this, "regionManager", false);
        inventories = new Inventories();
        messages = new Messages();
    }

    public void reloadConfigs() {
        Optional.ofNullable(guardians).ifPresent(Guardians::reload);
        Optional.ofNullable(farmManager).ifPresent(RegionsManager::reload);
        Optional.ofNullable(configuration).ifPresent(Config::reload);
    }

    public void saveConfigs() {
        Optional.ofNullable(guardians).ifPresent(Guardians::reload);
    }
}