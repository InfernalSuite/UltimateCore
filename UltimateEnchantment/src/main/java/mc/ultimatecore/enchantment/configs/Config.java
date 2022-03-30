package mc.ultimatecore.enchantment.configs;

import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.Item;
import mc.ultimatecore.enchantment.object.AdvancedSettings;
import mc.ultimatecore.enchantment.object.LoreAddition;
import mc.ultimatecore.enchantment.object.LoreSettings;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config extends YAMLFile{
    public String prefix = "&e&lUltimateCore-Enchantments &7";
    public String mainCommandPerm = "";
    public boolean setAsDefaultEnchantingTable = true;
    public boolean addInfoOnEnchant = true;
    public List<String> infoToEnchantedItem = Arrays.asList("&9%enchant_name% %enchant_level%", "&7%enchant_description%");
    public boolean hideOriginalEnchant = true;
    public boolean byPassEnchantmentRestrictions = false;
    public List<String> disabledWorlds = new ArrayList<>();
    public boolean loadAllEcoEnchants = false;
    public boolean loadAllAdvancedEnchants = false;
    public boolean removeIncompatibleEnchants = true;
    public AdvancedSettings advancedSettings;
    public LoreSettings loreSettings;
    public Item bookItem;
    public double aurelliumXP;
    public String aurelliumSkill;

    public Config(EnchantmentsPlugin enchantmentsPlugin, String name) {
        super(enchantmentsPlugin, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        YamlConfiguration cf = getConfig().get();
        prefix = cf.getString("prefix");
        mainCommandPerm = cf.getString("mainCommandPerm");
        setAsDefaultEnchantingTable = cf.getBoolean("setAsDefaultEnchantingTable");
        setAsDefaultEnchantingTable = cf.getBoolean("setAsDefaultEnchantingTable");
        addInfoOnEnchant = cf.getBoolean("addInfoOnEnchant");
        infoToEnchantedItem = cf.getStringList("infoToEnchantedItem");
        hideOriginalEnchant = cf.getBoolean("hideOriginalEnchant");
        byPassEnchantmentRestrictions = cf.getBoolean("byPassEnchantmentRestrictions");
        disabledWorlds = cf.getStringList("disabledWorlds");
        loadAllEcoEnchants = cf.getBoolean("loadAllEcoEnchants");
        loadAllAdvancedEnchants = cf.getBoolean("loadAllAdvancedEnchants");
        removeIncompatibleEnchants = cf.getBoolean("removeIncompatibleEnchants");
        advancedSettings = new AdvancedSettings(cf.getInt("advancedSettings.limit"),
                cf.getInt("advancedSettings.perLine"),
                cf.getString("advancedSettings.line"));
        loreSettings = new LoreSettings(LoreAddition.valueOf(cf.getString("loreSettings.type")), cf.getInt("loreSettings.line"));
        bookItem = Utils.getItemFromConfig(getConfig().get(), "bookItem");
        aurelliumXP = getConfig().get().getDouble("aurelliumSkills.xp");
        aurelliumSkill = getConfig().get().getString("aurelliumSkills.skill");
    }

}