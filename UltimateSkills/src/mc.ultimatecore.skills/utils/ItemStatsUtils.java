package mc.ultimatecore.skills.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemStatsUtils {
    public static Map<Ability, Double> getItemAbilities(final NBTItem itemStack, boolean weapon){
        String name = itemStack.getItem().getType().toString();
        Map<Ability, Double> abilities = weapon ? new HashMap<>() : HyperSkills.getInstance().getNormalItems().getArmorAbility(name);
        for(Ability ability : Ability.values()){
            if(!itemStack.hasKey(ability.toString())) continue;
            double quantity = itemStack.getDouble(ability.toString());
            if(quantity != 0) abilities.put(ability, quantity);
        }
        return abilities;
    }

    public static Map<Perk, Double> getItemPerks(NBTItem itemStack, boolean weapon){
        String name = itemStack.getItem().getType().toString();
        Map<Perk, Double> perks = weapon ? new HashMap<>() : HyperSkills.getInstance().getNormalItems().getArmorPerk(name);
        for(Perk ability : Perk.values()){
            if(!itemStack.hasKey(ability.toString())) continue;
            double quantity = itemStack.getDouble(ability.toString());
            if(quantity != 0) perks.put(ability, quantity);
        }
        return perks;
    }

    public static Double getManaCost(NBTItem nbtItem){
        if(nbtItem.hasKey("manaCost"))
            return nbtItem.getDouble("manaCost");
        return 0D;
    }

    public static Double getMMOManaCost(NBTItem nbtItem) {
        if (nbtItem.hasKey("MMOITEMS_MANA_COST"))
            return nbtItem.getDouble("MMOITEMS_MANA_COST");
        return 0D;
    }

    public static void setupArmorAbilities(Player player){
        if(player == null) return;
        for(ItemStack itemStack : player.getInventory().getArmorContents()){
            if(itemStack == null || itemStack.getType() == Material.AIR) continue;
            NBTItem armor = new NBTItem(itemStack);
            if(Utils.hasEffectInHand(armor.getItem())) continue;
            ItemStatsUtils.getItemAbilities(armor, false).forEach((ability, value) -> HyperSkills.getInstance().getApi().addArmorAbility(player.getUniqueId(), ability, value));
        }
    }
}
