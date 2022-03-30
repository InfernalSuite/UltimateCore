package com.infernalsuite.ultimatecore.pets.addons;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.stats.Stat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AureliumAPIManager {
    private final AureliumSkills plugin = (AureliumSkills) Bukkit.getPluginManager().getPlugin("AureliumSkills");

    public void addStats(Player player, Stat stat, double value){
        AureliumAPI.addStatModifier(player, "HyperPets_"+stat.name(), stat, value);
    }

    public void removeStats(Player player, Stat stat){
        AureliumAPI.removeStatModifier(player, "HyperPets_"+stat.name());
    }

    public Stat getStat(String key){
        return plugin.getStatRegistry().getStat(key);
    }

}
