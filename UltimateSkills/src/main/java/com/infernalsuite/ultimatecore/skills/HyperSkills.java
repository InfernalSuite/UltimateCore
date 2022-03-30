package com.infernalsuite.ultimatecore.skills;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.skills.api.HyperSkillsAPI;
import com.infernalsuite.ultimatecore.skills.armorequipevent.ArmorListener;
import com.infernalsuite.ultimatecore.skills.configs.*;
import com.infernalsuite.ultimatecore.skills.database.Database;
import com.infernalsuite.ultimatecore.skills.database.implementations.MySQLDatabase;
import com.infernalsuite.ultimatecore.skills.database.implementations.SQLiteDatabase;
import com.infernalsuite.ultimatecore.skills.listener.*;
import com.infernalsuite.ultimatecore.skills.listener.perks.AlchemyPerks;
import com.infernalsuite.ultimatecore.skills.listener.perks.DamageListener;
import com.infernalsuite.ultimatecore.skills.listener.perks.DefenseListener;
import com.infernalsuite.ultimatecore.skills.listener.perks.EnchantingPerks;
import com.infernalsuite.ultimatecore.skills.listener.skills.*;
import com.infernalsuite.ultimatecore.skills.managers.*;
import com.infernalsuite.ultimatecore.skills.serializer.GSON;
import lombok.Getter;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.database.Credentials;
import com.infernalsuite.ultimatecore.helper.database.DatabaseType;
import com.infernalsuite.ultimatecore.skills.api.HyperSkillsAPIImpl;
import com.infernalsuite.ultimatecore.skills.commands.CommandManager;
import mc.ultimatecore.skills.configs.*;
import mc.ultimatecore.skills.listener.*;
import mc.ultimatecore.skills.listener.skills.*;
import mc.ultimatecore.skills.managers.*;
import com.infernalsuite.ultimatecore.skills.objects.DebugType;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class HyperSkills extends UltimatePlugin {
    
    private Config configuration;
    private Messages messages;
    private Rewards rewards;
    private Inventories inventories;
    private Requirements requirements;
    private SkillsPoints skillPoints;
    private UltimateItems ultimateItems;
    private HyperSkillsAPI api;
    private Database pluginDatabase;
    private Skills skills;
    private NormalItems normalItems;
    private CommandManager commandManager;
    private AddonsManager addonsManager;
    private SkillManager skillManager;
    private ManaManager manaManager;
    private AbilitiesManager abilitiesManager;
    private PerksManager perksManager;
    private ActionBarManager actionBarManager;
    private ResetDataManager resetDataManager;
    private SpeedManager speedManager;
    private HealthManager healthManager;
    private GSON gson;
    
    public static HyperSkills getInstance() {
        return HyperSkills.getPlugin(HyperSkills.class);
    }
    
    @Override
    public void onEnable() {
        this.gson = new GSON();
        loadConfigs();
        Credentials credentials = Credentials.fromConfig(configuration.getConfig());
        this.pluginDatabase = credentials.getDatabaseType() == DatabaseType.MYSQL ? new MySQLDatabase(this, credentials) : new SQLiteDatabase(this, credentials);
        this.api = new HyperSkillsAPIImpl(this);
        this.skillManager = new SkillManager(this);
        this.perksManager = new PerksManager(this);
        this.abilitiesManager = new AbilitiesManager(this);
        this.addonsManager = new AddonsManager(this);
        this.commandManager = new CommandManager(this);
        this.actionBarManager = new ActionBarManager(this);
        this.resetDataManager = new ResetDataManager(this);
        this.manaManager = new ManaManager(this);
        this.speedManager = new SpeedManager(this);
        this.healthManager = new HealthManager(this);
        registerListeners(new DamageListener(this), new ArmorListener(new ArrayList<>()), new BlockBreakListener(this), new ArmorSetupListener(), new ArmorEquipListener(), new AlchemyListener(this), XMaterial.getVersion() == 8 ? new MobKillListener_Legacy(this) : new MobKillListener(this), new PlayerJoinLeaveListener(this), new EnchantingListener(this), new FishingListener(this), new AlchemyPerks(), new DefenseListener(this), new EnchantingPerks(), new BlockPlaceListener(), new ItemStatsListener(this), new InventoryClickListener());
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Has been enabled!"));
    }
    
    
    @Override
    public void onDisable() {
        if (skillManager != null) skillManager.disable();
        if (perksManager != null) perksManager.disable();
        if (abilitiesManager != null) abilitiesManager.disable();
        if (pluginDatabase != null) pluginDatabase.close();
        Bukkit.getServer().getOnlinePlayers().forEach(HumanEntity::closeInventory);
        ultimateItems.save();
        getLogger().info(getDescription().getName() + " Disabled!");
    }
    
    public void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
    
    public void sendErrorMessage(Exception e) {
        e.printStackTrace();
    }
    
    public void loadConfigs() {
        normalItems = new NormalItems(this, "normalitems", false, true);
        ultimateItems = new UltimateItems(this, "ultimateitems", true, true);
        configuration = new Config(this, "config", true, false);
        messages = new Messages(this, "messages", true, false);
        rewards = new Rewards(this, "rewards", true, false);
        requirements = new Requirements(this, "requirements", true, false);
        skillPoints = new SkillsPoints(this, "skillspoints", true, false);
        inventories = new Inventories(this, "inventories", true, false);
        skills = new Skills(this, "skills", true, false);
    }
    
    public void reloadConfigs() {
        normalItems.reload();
        ultimateItems.reload();
        configuration.reload();
        messages.reload();
        rewards.reload();
        requirements.reload();
        skillPoints.reload();
        inventories.reload();
        skills.reload();
    }
    
    public void sendDebug(String message, DebugType debugType) {
        if (!configuration.debug) return;
        if (debugType == DebugType.LOG)
            getLogger().info(message);
        else
            Bukkit.getConsoleSender().sendMessage(StringUtils.color(message));
    }
    
    @Override
    public String getPluginName() {
        return getDescription().getName();
    }
}
