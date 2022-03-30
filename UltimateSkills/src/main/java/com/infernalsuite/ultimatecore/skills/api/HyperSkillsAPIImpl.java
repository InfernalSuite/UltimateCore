package com.infernalsuite.ultimatecore.skills.api;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import com.infernalsuite.ultimatecore.skills.objects.perks.PlayerPerks;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;

import java.util.UUID;

@AllArgsConstructor
public class HyperSkillsAPIImpl implements HyperSkillsAPI {

    private final HyperSkills plugin;

    @Override
    public int getLevel(UUID uuid, SkillType skill) {
        return plugin.getSkillManager().getLevel(uuid, skill);
    }

    @Override
    public double getXP(UUID uuid, SkillType skill) {
        return plugin.getSkillManager().getXP(uuid, skill);
    }

    @Override
    public void setLevel(UUID uuid, SkillType skill, int level) {
        plugin.getSkillManager().getPlayerSkills(uuid).setLevel(skill, level);
    }

    @Override
    public void setXP(UUID uuid, SkillType skill, double xp) {
        plugin.getSkillManager().setXP(uuid, skill, xp);
    }

    @Override
    public void addLevel(UUID uuid, SkillType skill, int level) {
        plugin.getSkillManager().getPlayerSkills(uuid).addLevel(skill, level);
    }

    @Override
    public void addXP(UUID uuid, SkillType skill, double xp) {
        plugin.getSkillManager().addXP(uuid, skill, xp);
    }

    @Override
    public void addAbility(UUID uuid, Ability ability, double quantity) {
        plugin.getAbilitiesManager().getPlayerAbilities(uuid).addAbility(ability, quantity);
    }

    @Override
    public void addArmorAbility(UUID uuid, Ability ability, double quantity) {
        plugin.getAbilitiesManager().getPlayerAbilities(uuid).addArmorAbility(ability, quantity);
    }

    @Override
    public void removeArmorAbility(UUID uuid, Ability ability, double quantity) {
        plugin.getAbilitiesManager().getPlayerAbilities(uuid).removeArmorAbility(ability, quantity);
    }

    @Override
    public void addArmorPerk(UUID uuid, Perk perk, double quantity) {
        plugin.getPerksManager().getPlayerPerks(uuid).addArmorPerk(perk, quantity);
    }

    @Override
    public void removeArmorPerk(UUID uuid, Perk perk, double quantity) {
        plugin.getPerksManager().getPlayerPerks(uuid).removeArmorPerk(perk, quantity);
    }

    @Override
    public void setAbility(UUID uuid, Ability ability, double quantity) {
        plugin.getAbilitiesManager().getPlayerAbilities(uuid).setAbility(ability, quantity);
    }

    @Override
    public void removeAbility(UUID uuid, Ability ability, double quantity) {
        plugin.getAbilitiesManager().getPlayerAbilities(uuid).removeAbility(ability, quantity);
    }

    @Override
    public double getTotalAbility(UUID uuid, Ability ability) {
        if (plugin.getAddonsManager().isMMOItems())
            return ability == Ability.Health ? plugin.getAddonsManager().getMmoItems().getStats(uuid, ability) + 80 : plugin.getAddonsManager().getMmoItems().getStats(uuid, ability);
        double simpleAbility = getSimpleAbility(uuid, ability);
        double extraAbility = getExtraAbility(uuid, ability);
        double total = Math.max(0, simpleAbility + extraAbility);
        return ability == Ability.Health ? total + 100 : total;
    }


    @Override
    public double getSimpleAbility(UUID uuid, Ability ability) {
        return plugin.getAbilitiesManager().getAbility(uuid, ability);
    }

    @Override
    public double getExtraAbility(UUID uuid, Ability ability) {
        if (plugin.getAddonsManager().isMMOItems())
            return plugin.getAbilitiesManager().getPlayerAbilities(uuid).getArmorAbility(ability) + plugin.getAddonsManager().getMmoItems().getMMOArmor(uuid, ability);
        else
            return plugin.getAbilitiesManager().getPlayerAbilities(uuid).getArmorAbility(ability);
    }

    @Override
    public void setPerk(UUID uuid, Perk perk, double quantity) {
        plugin.getPerksManager().getPlayerPerks(uuid).setPerk(perk, quantity);
    }

    @Override
    public void addPerk(UUID uuid, Perk perk, double quantity) {
        plugin.getPerksManager().getPlayerPerks(uuid).addPerk(perk, quantity);
    }

    @Override
    public void removePerk(UUID uuid, Perk perk, double quantity) {
        plugin.getPerksManager().getPlayerPerks(uuid).removePerk(perk, quantity);
    }

    @Override
    public double getSimplePerk(UUID uuid, Perk perk) {
        return plugin.getPerksManager().getPerk(uuid, perk);
    }

    @Override
    public double getExtraPerk(UUID uuid, Perk perk) {
        return plugin.getPerksManager().getPlayerPerks(uuid).getArmorPerk(perk);
    }

    @Override
    public double getRank(UUID uuid, SkillType skillType) {
        return plugin.getSkillManager().getPlayerSkills(uuid).getRankPosition(skillType);
    }

    @Override
    public double getTotalPerk(UUID uuid, Perk perk) {
        PlayerPerks playerPerks = plugin.getPerksManager().getPlayerPerks(uuid);
        if (playerPerks == null) return 0;
        double armorPerk = Math.max(playerPerks.getArmorPerk(perk), 0);
        return playerPerks.getPerk(perk) + armorPerk;
    }
}