package com.infernalsuite.ultimatecore.skills.objects.item;

import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UltimateItem {

    private final String id;

    private ItemStack itemStack;

    private boolean effectInHand;

    private double manaCost;

    private final HashMap<Ability, Double> abilitiesMap;

    private final HashMap<Perk, Double> perksMap;

    public UltimateItem(String id, ItemStack itemStack) {
        this.id = id;
        this.itemStack = itemStack;
        this.abilitiesMap = new HashMap<>();
        this.perksMap = new HashMap<>();
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public ItemStack getItem() {
        NBTItem nbtItem = new NBTItem(itemStack.clone());
        if (effectInHand) nbtItem.setBoolean("inHand", true);
        if (manaCost > 0) nbtItem.setDouble("manaCost", manaCost);
        abilitiesMap.forEach((ability, aDouble) -> nbtItem.setDouble(ability.toString(), aDouble));
        perksMap.forEach((perk, aDouble) -> nbtItem.setDouble(perk.toString(), aDouble));
        return nbtItem.getItem();
    }

    public double getAbility(Ability ability) {
        return abilitiesMap.getOrDefault(ability, 0d);
    }

    public double getPerk(Perk perk) {
        return perksMap.getOrDefault(perk, 0d);
    }

    public void setAbility(Ability ability, Double amount) {
        abilitiesMap.put(ability, amount);
    }

    public void setPerk(Perk ability, Double amount) {
        perksMap.put(ability, amount);
    }

    public void setDisplayName(String displayName) {
        try {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(StringUtils.color(displayName));
            itemStack.setItemMeta(meta);
        } catch (NullPointerException ignored) {
        }
    }

    public String getDisplayName() {
        try {
            ItemMeta meta = itemStack.getItemMeta();
            return meta.getDisplayName();
        } catch (NullPointerException e) {
            return "NONE";
        }
    }
}
