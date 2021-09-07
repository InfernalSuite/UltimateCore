package mc.ultimatecore.farm.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.guardians.Guardian;
import mc.ultimatecore.farm.guardians.implementations.LegacyGuardian;
import mc.ultimatecore.farm.guardians.implementations.NormalGuardian;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class Guardians extends YamlConfig {

    private final Map<String, Guardian> guardians = new HashMap<>();

    public Guardians(HyperRegions plugin, String name, boolean defaults) {
        super(plugin, name, defaults);
        loadDefaults();
    }

    public void save() {
        ConfigurationSection configurationSection = getConfig().getConfigurationSection("guardians");
        if (configurationSection != null) {
            for (String str : configurationSection.getKeys(false))
                getConfig().set("guardians." + str, null);
        }
        guardians.values().stream()
                .filter(Objects::nonNull)
                .filter(guardian -> guardian.getLocation() != null)
                .forEach(guardian -> Utils.saveLocationToConfig(getConfig(), "guardians." + guardian.getName(), guardian.getLocation()));
        super.save();
    }

    private void loadDefaults() {
        ConfigurationSection configurationSection = getConfig().getConfigurationSection("guardians");
        if (configurationSection == null) return;
        int amount = 0;
        for (String guardianName : configurationSection.getKeys(false)) {
            Location location = Utils.getLocationFromConfig(getConfig(), "guardians." + guardianName);
            addGuardian(guardianName, location, false);
            amount++;
        }
        if (amount > 0)
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e[UltimateFarm] &b" + amount + " Guardian" + (amount > 1 ? "s" : "") + " " + (amount > 1 ? "were" : "was") + " successfully loaded!"));
    }

    public boolean addGuardian(String guardianName, Location location, boolean enable) {
        if (!guardianExist(guardianName)) {
            Guardian guardian = XMaterial.getVersion() > 13 ? new NormalGuardian(guardianName, location, enable) : new LegacyGuardian(guardianName, location, enable);
            guardians.put(guardianName, guardian);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeGuardian(String guardianName) {
        if (guardianExist(guardianName)) {
            guardians.get(guardianName).remove();
            guardians.remove(guardianName);
            return true;
        } else {
            return false;
        }
    }

    public boolean guardianExist(String guardianName) {
        return guardians.containsKey(guardianName);
    }

    public Optional<Guardian> getGuardian(String name) {
        return guardians.values().stream().filter(guardian -> guardian.getName().equals(name)).findFirst();
    }

    public Collection<Guardian> getGuardians() {
        return guardians.values();
    }
}
