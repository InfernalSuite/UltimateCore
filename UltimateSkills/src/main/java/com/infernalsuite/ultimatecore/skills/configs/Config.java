package com.infernalsuite.ultimatecore.skills.configs;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.skills.enums.Status;
import com.infernalsuite.ultimatecore.skills.managers.IndicatorType;
import com.infernalsuite.ultimatecore.skills.objects.DamageIndicator;
import com.infernalsuite.ultimatecore.skills.objects.ManaSettings;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config extends YAMLFile {
    public String prefix;
    public int maxSkillLevel;
    public int refreshRankingMinutes;
    public int levelToRank;
    public boolean byPassWorldGuard;
    public boolean manaSystem;
    public boolean actionBar;
    public boolean actionBarXP;
    public boolean healthSystem;
    public boolean openMenuWithSkills;
    public boolean talismanSystem;
    public String levelUPSound;
    public String gainXPSound;
    public boolean scaledHealth;
    public boolean useDefaultSkillsXP;
    public int initialHealth;
    public int initialDefense;
    public int initialMana;
    public boolean resetDataConfirm;
    public boolean resetDataMessage;
    public boolean translatePAPIPlaceholders;
    public String damageSymbol;
    public Map<IndicatorType, DamageIndicator> indicators;
    public Map<Status, List<String>> rankInformation;
    public List<String> indicatorBlackList;
    public boolean getXPInCreative;
    public ManaSettings manaSettings;
    public boolean debug;
    public boolean jetMinions;
    public boolean ultraMinions;

    public Config(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        rankInformation = new HashMap<>();
        indicators = new HashMap<>();
        YamlConfiguration cf = getConfig();
        //------------------------------------------------//
        prefix = cf.getString("prefix");
        useDefaultSkillsXP = cf.getBoolean("useDefaultSkillsXP");
        maxSkillLevel = cf.getInt("maxSkillLevel");
        levelToRank = cf.getInt("levelToRank");
        byPassWorldGuard = cf.getBoolean("byPassWorldGuard");
        manaSystem = cf.getBoolean("manaSystem");
        actionBar = cf.getBoolean("actionBar");
        actionBarXP = cf.getBoolean("actionBarXP");
        healthSystem = cf.getBoolean("healthSystem");
        openMenuWithSkills = cf.getBoolean("openMenuWithSkills");
        talismanSystem = cf.getBoolean("talismanSystem");
        levelUPSound = cf.getString("levelUPSound");
        gainXPSound = cf.getString("gainXPSound");
        scaledHealth = cf.getBoolean("scaledHealth");
        rankInformation.put(Status.RANKED, cf.getStringList("rankInformation.RANKED"));
        rankInformation.put(Status.UNRANKED, cf.getStringList("rankInformation.UNRANKED"));
        resetDataConfirm = cf.getBoolean("resetDataConfirm");
        initialDefense = cf.getInt("initialAbilities.Defense");
        initialMana = cf.getInt("initialAbilities.Max_Intelligence");
        resetDataMessage = cf.getBoolean("resetDataMessage");
        damageSymbol = cf.getString("damageIndicators.symbol");
        refreshRankingMinutes = cf.getInt("refreshRankingMinutes");
        indicators.put(IndicatorType.CRITIC, new DamageIndicator(IndicatorType.CRITIC
                , cf.getBoolean("damageIndicators.critic.enabled")
                , cf.getBoolean("damageIndicators.normal.colorDegrade")
                , cf.getString("damageIndicators.critic.indicator")));
        indicators.put(IndicatorType.NORMAL, new DamageIndicator(IndicatorType.NORMAL
                , cf.getBoolean("damageIndicators.normal.enabled")
                , cf.getBoolean("damageIndicators.normal.colorDegrade")
                , cf.getString("damageIndicators.normal.indicator")));
        indicatorBlackList = cf.getStringList("damageIndicators.blacklist");
        translatePAPIPlaceholders = cf.getBoolean("translatePAPIPlaceholders");
        getXPInCreative = cf.getBoolean("getXPInCreative");
        manaSettings = new ManaSettings(cf.getDouble("manaSettings.percentagePerSecond"), cf.getInt("manaSettings.second"));
        debug = cf.getBoolean("DEBUG");
        jetMinions = cf.getBoolean("addons.jetMinions");
        ultraMinions = cf.getBoolean("addons.ultraMinions");
        //------------------------------------------------//
    }
}
