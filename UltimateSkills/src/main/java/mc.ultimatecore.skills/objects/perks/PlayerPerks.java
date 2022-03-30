package mc.ultimatecore.skills.objects.perks;

import java.util.HashMap;
import java.util.Map;

public class PlayerPerks {

    private final Map<Perk, Double> playerPerks = new HashMap<>();

    private final Map<Perk, Double> armorPerks = new HashMap<>();

    public PlayerPerks() {
        for (Perk perk : Perk.values()) {
            armorPerks.put(perk, 0D);
            playerPerks.put(perk, 0D);
        }
    }

    public void addPerk(Perk perk, Double quantity) {
        if (playerPerks.containsKey(perk))
            playerPerks.put(perk, playerPerks.get(perk) + quantity);
    }

    public void removePerk(Perk perk, Double quantity) {
        if (playerPerks.containsKey(perk))
            playerPerks.put(perk, playerPerks.get(perk) - quantity);
    }

    public Double getPerk(Perk perk) {
        if (playerPerks.containsKey(perk))
            return playerPerks.get(perk);
        return 0D;
    }

    public Double getArmorPerk(Perk perk) {
        if (armorPerks.containsKey(perk))
            return armorPerks.get(perk);
        return 0D;
    }

    public void setPerk(Perk perk, Double quantity) {
        if (playerPerks.containsKey(perk))
            playerPerks.put(perk, quantity);
    }

    public void addArmorPerk(Perk perk, Double quantity) {
        if (armorPerks.containsKey(perk))
            armorPerks.put(perk, armorPerks.get(perk) + quantity);
    }

    public void removeArmorPerk(Perk perk, Double quantity) {
        if (armorPerks.containsKey(perk))
            armorPerks.put(perk, armorPerks.get(perk) - quantity);
    }
}
