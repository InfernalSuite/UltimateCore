package mc.ultimatecore.talismans.configs;

import lombok.Getter;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.talismans.objects.ImmunityType;
import mc.ultimatecore.talismans.objects.Talisman;
import mc.ultimatecore.talismans.objects.TalismanType;
import mc.ultimatecore.talismans.objects.implementations.*;
import mc.ultimatecore.talismans.utils.SkillsUtils;
import mc.ultimatecore.talismans.utils.StringUtils;
import mc.ultimatecore.talismans.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Talismans extends YAMLFile {
    
    private Map<String, Talisman> talismans;
    
    public Talismans(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadTalismans();
    }
    
    @Override
    public void reload() {
        super.reload();
        this.loadTalismans();
    }
    
    
    private void loadTalismans() {
        talismans = new HashMap<>();
        boolean skills = Bukkit.getServer().getPluginManager().getPlugin("UltimateCore-Skills") != null;
        ConfigurationSection section = getConfig().getConfigurationSection("talismans");
        int loaded = 0;
        if (section == null) return;
        for (String key : section.getKeys(false)) {
            TalismanType talismanType = TalismanType.valueOf(getConfig().getString("talismans." + key + ".type"));
            Talisman talisman = null;
            String displayName = getConfig().getString("talismans." + key + ".displayName");
            List<String> lore = getConfig().getStringList("talismans." + key + ".lore");
            String texture = getConfig().getString("talismans." + key + ".texture");
            Map<Ability, Double> ability = null;
            Map<Perk, Double> perk = null;
            Map<ImmunityType, Double> immunities = null;
            List<String> regions = null;
            String command = null;
            int seconds = 0;
            if (getConfig().contains("talismans." + key + ".texture"))
                regions = getConfig().getStringList("talismans." + key + ".regions");
            if (getConfig().contains("talismans." + key + ".abilities") && skills)
                ability = SkillsUtils.getTalismanAbilities(getConfig(), "talismans." + key + ".abilities");
            if (getConfig().contains("talismans." + key + ".perks") && skills)
                perk = SkillsUtils.getTalismanPerks(getConfig(), "talismans." + key + ".perks");
            if (getConfig().contains("talismans." + key + ".immunities"))
                immunities = Utils.getTalismanImmunities(getConfig(), "talismans." + key + ".immunities");
            if (getConfig().contains("talismans." + key + ".command"))
                command = getConfig().getString("talismans." + key + ".command");
            if (getConfig().contains("talismans." + key + ".command"))
                seconds = getConfig().getInt("talismans." + key + ".seconds");
            switch (talismanType) {
                case PERK:
                    if (perk != null) talisman = new PerkTalisman(key, talismanType, displayName, lore, texture, perk, true);
                    break;
                case PERK_REGION:
                    if (perk != null) talisman = new PerkRegionTalisman(key, talismanType, displayName, lore, texture, perk, regions);
                    break;
                case ABILITY:
                    if (ability != null) talisman = new AbilityTalisman(key, talismanType, displayName, lore, texture, ability, true);
                    break;
                case ABILITY_REGION:
                    if (ability != null && regions != null) talisman = new AbilityRegionTalisman(key, talismanType, displayName, lore, texture, ability, regions);
                    break;
                case TIMER:
                    if (command != null) talisman = new TimerTalisman(key, talismanType, displayName, lore, texture, seconds, command);
                    break;
                case IMMUNITY:
                    if (immunities != null) talisman = new ImmunityTalisman(key, talismanType, displayName, lore, texture, immunities);
            }
            if (talisman == null) continue;
            talismans.put(key, talisman);
            loaded++;
        }
        if (loaded == 0) return;
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperTalismans] &aSuccessfully loaded " + loaded + " talismans!"));
    }
    
    public Talisman getTalisman(String name) {
        if (!talismans.containsKey(name)) return null;
        return talismans.get(name);
    }
}