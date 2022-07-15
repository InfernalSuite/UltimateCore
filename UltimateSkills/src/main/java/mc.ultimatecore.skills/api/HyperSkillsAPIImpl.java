package mc.ultimatecore.skills.api;

import lombok.AllArgsConstructor;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.objects.perks.PlayerPerks;

import java.util.*;

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
        double simpleAbility = getSimpleAbility(uuid, ability);
        double extraAbility = getExtraAbility(uuid, ability);
        double total = Math.max(0, simpleAbility + extraAbility);
        if (plugin.getAddonsManager().isMMOItems()) {
            if(ability == Ability.HEALTH) {
                total += plugin.getAddonsManager().getMmoItems().getStats(uuid, ability);
            }
        }
        return ability == Ability.HEALTH ? total + 80 : total;
    }


    @Override
    public double getSimpleAbility(UUID uuid, Ability ability) {
        return plugin.getAbilitiesManager().getAbility(uuid, ability);
    }

    @Override
    public double getExtraAbility(UUID uuid, Ability ability) {
        if (plugin.getAddonsManager().isMMOItems()) {
            return plugin.getAbilitiesManager().getPlayerAbilities(uuid).getArmorAbility(ability) + plugin.getAddonsManager().getMmoItems().getMMOArmor(uuid, ability);
        }else {
            return plugin.getAbilitiesManager().getPlayerAbilities(uuid).getArmorAbility(ability);
        }
    }

    @Override
    public void setPerk(UUID uuid, Perk perk, double quantity) {
        plugin.getPerksManager().getPlayerPerks(uuid).setPerk(perk, quantity);
    }

    @Override
    public void addPerk(UUID uuid, Perk perk, double quantity) {
        try {
            var t = plugin.getPerksManager();
            var x = t.getPlayerPerks(uuid);
            x.addPerk(perk, quantity);
        }catch (Exception e) {
            var t = plugin.getPerksManager();
            System.out.println("[DEBUG ADDPERK 1]" + uuid);
            System.out.println("[DEBUG ADDPERK 2]" + Arrays.toString(t.perksCache.keySet().toArray()));
        }
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

    @Override
    public boolean useMana(UUID uuid, int quantity) {
        // needs implementation
        return false;
    }

    @Override
    public double getMana(UUID uuid) {
        return getTotalAbility(uuid, Ability.INTELLIGENCE);
    }
}