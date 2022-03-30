package com.infernalsuite.ultimatecore.skills.objects.abilities;

import java.util.HashMap;

public class PlayerAbilities {

    private final HashMap<Ability, Double> playerAbilities = new HashMap<>();

    private final HashMap<Ability, Double> armorAbilities = new HashMap<>();

    public PlayerAbilities() {
        for (Ability ability : Ability.values()) {
            armorAbilities.put(ability, 0D);
            playerAbilities.put(ability, 0D);
        }
    }

    public void addAbility(Ability ability, Double quantity) {
        if (playerAbilities.containsKey(ability))
            playerAbilities.put(ability, playerAbilities.get(ability) + quantity);
    }

    public void removeAbility(Ability ability, Double quantity) {
        if (playerAbilities.containsKey(ability))
            playerAbilities.put(ability, playerAbilities.get(ability) - quantity);

    }

    public Double getAbility(Ability ability) {
        if (playerAbilities.containsKey(ability))
            return playerAbilities.get(ability);
        return 0D;
    }

    public void setAbility(Ability ability, Double quantity) {
        if (playerAbilities.containsKey(ability))
            playerAbilities.put(ability, quantity);
    }

    public Double getArmorAbility(Ability ability) {
        if (armorAbilities.containsKey(ability))
            return armorAbilities.get(ability);
        return 0D;
    }

    public void addArmorAbility(Ability ability, Double quantity) {
        if (armorAbilities.containsKey(ability))
            armorAbilities.put(ability, armorAbilities.get(ability) + quantity);
    }

    public void removeArmorAbility(Ability ability, Double quantity) {
        if (armorAbilities.containsKey(ability))
            armorAbilities.put(ability, armorAbilities.get(ability) - quantity);
    }
}
