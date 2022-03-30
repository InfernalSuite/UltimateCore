package com.infernalsuite.ultimatecore.skills;

import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;

public class TempUser {
    public String player;

    public String name;

    private final transient Map<Ability, Double> tempAbility;

    private final transient Map<Perk, Double> tempPerk;

    public TempUser(OfflinePlayer p) {
        this.player = p.getUniqueId().toString();
        this.name = p.getName();
        this.tempPerk = new HashMap<>();
        this.tempAbility = new HashMap<>();
        (HyperSkills.getInstance().getSkillManager()).tempUsers.put(p.getUniqueId(), this);
    }

    public static TempUser getUser(OfflinePlayer p) {
        if (p == null)
            return null;
        return (HyperSkills.getInstance().getSkillManager()).tempUsers.containsKey(p.getUniqueId()) ? HyperSkills.getInstance().getSkillManager().tempUsers.get(p.getUniqueId()) : new TempUser(p);
    }

    public Map<Ability, Double> getTempAbility() {
        return tempAbility;
    }

    public void setTempAbility(Map<Ability, Double> tempAbility) {
        tempAbility.forEach(this.tempAbility::put);
    }

    public Map<Perk, Double> getTempPerk() {
        return this.tempPerk;
    }

    public void setTempPerk(Map<Perk, Double> tempPerk) {
        tempPerk.forEach(this.tempPerk::put);
    }
}
