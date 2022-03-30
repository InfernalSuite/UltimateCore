package com.infernalsuite.ultimatecore.menu.utils;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.menu.HyperCore;
import com.infernalsuite.ultimatecore.menu.Item;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.objects.PetData;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.api.HyperSkillsAPI;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Utils {

    private static String roundStr(Double d) {
        return new DecimalFormat("#.#").format(d);
    }

    public static List<Placeholder> getPlaceholders(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        String currentPet = "&c✘";
        if (HyperCore.getInstance().isPets()) {
            PetData petData = HyperPets.getInstance().getApi().getPetData(uuid);
            if (petData != null && HyperPets.getInstance().getApi().getPet(petData) != null)
                currentPet = HyperPets.getInstance().getApi().getPet(petData).getDisplayName();
        }
        HyperSkillsAPI api = HyperSkills.getInstance().getApi();
        return new ArrayList<>(Arrays.asList(
                new Placeholder("defense", roundStr(api.getTotalAbility(uuid, Ability.Defense))),
                new Placeholder("crit_chance", roundStr(api.getTotalAbility(uuid, Ability.Crit_Chance))),
                new Placeholder("crit_damage", roundStr(api.getTotalAbility(uuid, Ability.Crit_Damage))),
                new Placeholder("health", roundStr(api.getTotalAbility(uuid, Ability.Health))),
                new Placeholder("pet_luck", roundStr(api.getTotalAbility(uuid, Ability.Pet_Luck))),
                new Placeholder("speed", roundStr(api.getTotalAbility(uuid, Ability.Speed))),
                new Placeholder("strength", roundStr(api.getTotalAbility(uuid, Ability.Strength))),
                new Placeholder("player", offlinePlayer.getName()),
                new Placeholder("max_intelligence", roundStr(api.getTotalAbility(uuid, Ability.Max_Intelligence))),
                new Placeholder("max_intelligence", roundStr(api.getTotalAbility(uuid, Ability.Max_Intelligence))),
                new Placeholder("current_pet", currentPet),

                new Placeholder("intelligence", round(api.getTotalAbility(uuid, Ability.Intelligence)))));
    }

    private static String round(double str) {
        return String.valueOf(Math.round(str));
    }

    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material"))
            item.material = XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".headOwner")) item.headOwner = yamlConfig.getString(path + ".headOwner");
        if (yamlConfig.contains(path + ".headData")) item.headData = yamlConfig.getString(path + ".headData");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        return item;
    }

}
