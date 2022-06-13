package mc.ultimatecore.skills.addons;

import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import mc.ultimatecore.skills.implementations.SoftDependImpl;
import mc.ultimatecore.skills.objects.abilities.Ability;
import org.bukkit.*;

import java.util.UUID;

public class MMOItemsAPIManager extends SoftDependImpl{

    public MMOItemsAPIManager(String displayName) {
        super(displayName);
    }

    public void updateStats(UUID uuid, Ability ability, double value, String type) {
        StatMap stats = MMOPlayerData.get(uuid).getStatMap();
        StatInstance attribute = stats.getInstance(ability.getMmoItems());
        StatModifier modifier = attribute.getModifier("HC_" + ability + type);
        if(modifier != null) {
            if(modifier.getValue() != value) {
                if (modifier.getKey() != null) {
                    attribute.addModifier(new StatModifier(modifier.getKey(), modifier.getStat(), value, modifier.getType(), modifier.getSlot(), modifier.getSource()));
                }
            }
        }
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
            StatModifier modifier = attribute.getModifier("MMOItem");
            return modifier == null ? 0 : modifier.getValue();
        }
    }
}
