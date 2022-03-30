package com.infernalsuite.ultimatecore.reforge.configs;

import com.cryptomorin.xseries.XEnchantment;
import com.infernalsuite.ultimatecore.reforge.HyperReforge;
import com.infernalsuite.ultimatecore.reforge.enums.ItemType;
import com.infernalsuite.ultimatecore.reforge.objects.ChanceObject;
import com.infernalsuite.ultimatecore.reforge.objects.EnchantmentProbability;
import com.infernalsuite.ultimatecore.reforge.utils.StringUtils;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Chances extends YAMLFile{

    public Chances(HyperReforge hyperSkills, String name) {
        super(hyperSkills, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        int count = 0;
        for(ItemType itemType : ItemType.values()){
            String name = itemType.name;
            ConfigurationSection section = getConfig().get().getConfigurationSection(name);
            if(section == null){
                Bukkit.getConsoleSender().sendMessage(Utils.color("&e[HyperReforge] &cSettings for "+ name+" wasn't found!"));
                continue;
            }
            double costPerEnchantment = getConfig().get().getDouble(name+".costPerEnchantment");
            int newEnchantsPerEnchant = getConfig().get().getInt(name+".newEnchantsPerEnchant");
            ConfigurationSection chancesSection = getConfig().get().getConfigurationSection(name+".chances");
            if(chancesSection == null){
                Bukkit.getConsoleSender().sendMessage(Utils.color("&e[HyperReforge] &cChances for "+ name+" wasn't found!"));
                continue;
            }
            List<EnchantmentProbability> enchantmentProbabilities = new ArrayList<>();
            for(String key : chancesSection.getKeys(false)){
                String[] enchantment = getConfig().get().getString(name+".chances."+key+".enchantment").split(":");
                if(enchantment.length != 2) continue;
                HyperEnchant hyperEnchant = EnchantmentsPlugin.getInstance().getApi().getEnchantmentInstance(enchantment[0]);
                if(hyperEnchant == null){
                    try {
                        String enchName = Utils.getEnchantmentKey(XEnchantment.valueOf(enchantment[0]).parseEnchantment());
                        hyperEnchant = EnchantmentsPlugin.getInstance().getApi().getEnchantmentInstance(enchName);
                        if(hyperEnchant == null) {
                            Bukkit.getConsoleSender().sendMessage(Utils.color("&c[HyperReforge] &c"+enchantment[0] +" is not recognized as an enchantment!"));
                            continue;
                        }
                    }catch (Exception e){
                        Bukkit.getConsoleSender().sendMessage(Utils.color("&c[HyperReforge] &c"+enchantment[0] +" is not recognized as an enchantment!"));
                        continue;
                    }
                }
                int level = Integer.parseInt(enchantment[1]);
                int chance = getConfig().get().getInt(name+".chances."+key+".chance");
                enchantmentProbabilities.add(new EnchantmentProbability(key, hyperEnchant, level, chance));
            }

            if(enchantmentProbabilities.isEmpty()) {
                continue;
            }
            ChanceObject chanceObject = new ChanceObject(itemType, costPerEnchantment, newEnchantsPerEnchant, enchantmentProbabilities);

            HyperReforge.getInstance().getChancesManager().addChance(chanceObject);
            count++;
        }
        if(count > 0)
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperReforge] &a"+count+" Has been successfully loaded!"));

    }
}
