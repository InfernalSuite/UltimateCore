package com.infernalsuite.ultimatecore.runes.configs;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.runes.enums.RuneEffect;
import com.infernalsuite.ultimatecore.runes.enums.RuneType;
import com.infernalsuite.ultimatecore.runes.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.runes.utils.Placeholder;
import com.infernalsuite.ultimatecore.runes.utils.StringUtils;
import com.infernalsuite.ultimatecore.runes.utils.Utils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.runes.HyperRunes;
import com.infernalsuite.ultimatecore.runes.Item;
import com.infernalsuite.ultimatecore.runes.objects.Rune;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runes extends YAMLFile {
    
    public List<Rune> runes;
    
    public Runes(HyperRunes hyperRunes, String name) {
        super(hyperRunes, name);
    }
    
    @Override
    public void enable() {
        super.enable();
        loadDefaults();
    }
    
    @Override
    public void reload() {
        getConfig().reload();
        loadDefaults();
    }
    
    private void loadDefaults() {
        runes = new ArrayList<>();
        //MESSAGES
        for (String key : getConfig().get().getConfigurationSection("runes").getKeys(false)) {
            String runeName = getConfig().get().getString("runes." + key + ".runeName");
            String displayName = getConfig().get().getString("runes." + key + ".displayName");
            List<String> lore = getConfig().get().getStringList("runes." + key + ".lore");
            String texture = getConfig().get().getString("runes." + key + ".texture");
            RuneEffect runeEffect = RuneEffect.valueOf(getConfig().get().getString("runes." + key + ".effect"));
            RuneType runeType = RuneType.valueOf(getConfig().get().getString("runes." + key + ".runeType"));
            Rune rune = new Rune(runeName, displayName, lore, texture, runeEffect, runeType);
            runes.add(rune);
        }
    }
    
    public Rune getRuneByName(String name) {
        for (Rune rune : runes) {
            if (rune.getRuneName().equalsIgnoreCase(name))
                return rune;
        }
        return null;
    }
    
    
    public ItemStack getRune(String runeName, int level) {
        Rune rune = getRuneByName(runeName);
        ItemStack itemStack = InventoryUtils.makeItem(HyperRunes.getInstance().getRunes().getRuneItem(rune), Arrays.asList(new Placeholder("rune_level", Utils.toRoman(level)), new Placeholder("required_level", String.valueOf(rune.getRequiredLevel(level)))));
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("runeLevel", level);
        nbtItem.setString("runeName", rune.getRuneName());
        nbtItem.setString("runeType", rune.getRuneType().toString());
        return nbtItem.getItem();
    }
    
    
    public Item getRuneItem(Rune rune) {
        return new Item(XMaterial.PLAYER_HEAD, 49, rune.getTexture(), 1, StringUtils.color(rune.getDisplayName()), rune.getLore());
    }
    
    
}
