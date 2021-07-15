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
import mc.ultimatecore.farm.nms.NMS;
import mc.ultimatecore.farm.utils.Utils;
import mc.ultimatecore.helper.UltimatePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;

@Getter
public class HyperRegions extends UltimatePlugin {
    
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
        if (nms == null)
            Bukkit.getPluginManager().disablePlugin(this);
        
        addonsManager = new AddonsManager(this);
        
        loadConfigs();
        
        registerListeners(new InventoryClickListener(), new CropBreakListener(this));
        
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), this::saveConfigs, 1000L, 1900L);
        commandManager = new CommandManager("hyperregions");
        commandManager.registerCommands();
        
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " Has been enabled! &fVersion: " + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " Thanks for using my plugin!  &f~Reb4ck"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " &a[Important] Since version 4.1.12 Plugin can be configured only through config file"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " &a            this allow you to load regions easily with /HyperRegions reload."));
        
        
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
        if (guardians != null) guardians.getGuardians().forEach(Guardian::remove);
        if (farmManager != null) farmManager.blockRegenCache.forEach(blockRegen -> blockRegen.disableRegen(true));
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
        if (guardians != null) guardians.reload();
        if (farmManager != null) farmManager.reload();
        if (configuration != null) configuration.reload();
    }
    
    public void saveConfigs() {
        if (guardians != null) guardians.save();
    }
}