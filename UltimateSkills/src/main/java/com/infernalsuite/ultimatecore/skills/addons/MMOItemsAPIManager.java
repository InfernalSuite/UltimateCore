package com.infernalsuite.ultimatecore.skills.addons;

import com.infernalsuite.ultimatecore.skills.implementations.SoftDependImpl;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;

import java.util.UUID;

public class MMOItemsAPIManager extends SoftDependImpl {

    public MMOItemsAPIManager(String displayName) {
        super(displayName);
    }

    public void updateStats(UUID uuid, Ability ability, double value, String type) {
        StatMap stats = MMOPlayerData.get(uuid).getStatMap();
        StatInstance attribute = stats.getInstance(ability.getMmoItems());
        StatModifier modifier = attribute.getAttribute("HC_" + ability + type);
        if(modifier == null || modifier.getValue() != value)
            attribute.addModifier("HC_" + ability + type, new StatModifier(value));
    }

    public double getStats(UUID uuid, Ability ability) {
        if (MMOPlayerData.get(uuid) == null) {
            return 0;
        } else {
            StatMap stats = MMOPlayerData.get(uuid).getStatMap();
            return stats.getStat(ability.getMmoItems());
        }
    }

    public double getMMOArmor(UUID uuid, Ability ability) {
        if (MMOPlayerData.get(uuid) == null) {
            return 0;
        } else {
            StatMap stats = MMOPlayerData.get(uuid).getStatMap();
            StatInstance attribute = stats.getInstance(ability.getMmoItems());
            StatModifier modifier = attribute.getAttribute("MMOItem");
            return modifier == null ? 0 : modifier.getValue();
        }
    }
}
