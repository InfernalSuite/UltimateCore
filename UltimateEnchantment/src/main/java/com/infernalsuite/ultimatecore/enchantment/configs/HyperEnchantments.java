package com.infernalsuite.ultimatecore.enchantment.configs;

import com.cryptomorin.xseries.XEnchantment;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import com.infernalsuite.ultimatecore.enchantment.enchantments.NormalEnchantment;
import com.infernalsuite.ultimatecore.enchantment.enchantments.hooks.HyperAdvancedEnchantment;
import com.infernalsuite.ultimatecore.enchantment.enchantments.hooks.HyperEcoEnchant;
import com.infernalsuite.ultimatecore.enchantment.utils.AEUtils;
import com.infernalsuite.ultimatecore.enchantment.utils.EcoUtils;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import net.advancedplugins.ae.api.AEAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HyperEnchantments extends YAMLFile {
    
    public HashMap<String, HyperEnchant> enchantments;
    
    
    public HyperEnchantments(EnchantmentsPlugin enchantmentsPlugin, String name) {
        super(enchantmentsPlugin, name);
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
        enchantments = new HashMap<>();
        ConfigurationSection section = getConfig().get().getConfigurationSection("hyperEnchantments");
        if (section == null) return;
        int ad = 0;
        int van = 0;
        int eco = 0;
        for (String enchantID : section.getKeys(false)) {
            try {
                if (getConfig().get().contains("hyperEnchantments." + enchantID + ".enabled") && !getConfig().get().getBoolean("hyperEnchantments." + enchantID + ".enabled")) continue;
                String displayName = getConfig().get().getString("hyperEnchantments." + enchantID + ".displayName");
                boolean useMoney = getConfig().get().getBoolean("hyperEnchantments." + enchantID + ".useMoney");
                HashMap<Integer, Double> requiredMoney = getConfig().get().contains("hyperEnchantments." + enchantID + ".requiredMoney") ? Utils.deserializeRequiredMoney(getConfig().get().getStringList("hyperEnchantments." + enchantID + ".requiredMoney")) : new HashMap<>();
                String descriptionYAML = getConfig().get().getString("hyperEnchantments." + enchantID + ".description");
                HashMap<Integer, Integer> requiredLevel = getConfig().get().contains("hyperEnchantments." + enchantID + ".requiredLevel") ? Utils.deserializeRequiredLevels(getConfig().get().getStringList("hyperEnchantments." + enchantID + ".requiredLevel")) : new HashMap<>();
                int requiredBook = getConfig().get().getInt("hyperEnchantments." + enchantID + ".requiredBookShelf");
                HyperEnchant hyperEnchant;
                if (getConfig().get().contains("hyperEnchantments." + enchantID + ".advancedEnchantment") && EnchantmentsPlugin.getInstance().isAdvancedEnchantments() && AEAPI.isAnEnchantment(enchantID)) {
                    hyperEnchant = new HyperAdvancedEnchantment(displayName, 1, useMoney, requiredMoney, enchantID, requiredLevel, requiredBook);
                    ad++;
                } else if (getConfig().get().contains("hyperEnchantments." + enchantID + ".ecoEnchant") && EnchantmentsPlugin.getInstance().isEcoEnchants()) {
                    hyperEnchant = new HyperEcoEnchant(displayName, 1, useMoney, requiredMoney, enchantID, requiredLevel, requiredBook);
                    eco++;
                } else {
                    String key = Utils.getEnchantmentKey(XEnchantment.valueOf(enchantID).getEnchant());
                    assert descriptionYAML != null;
                    hyperEnchant = new NormalEnchantment(displayName, 1, useMoney, requiredMoney, enchantID, descriptionYAML, requiredLevel, requiredBook);
                    van++;
                    enchantments.put(key, hyperEnchant);
                    continue;
                }
                enchantments.put(enchantID.toLowerCase(), hyperEnchant);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + EnchantmentsPlugin.getInstance().getDescription().getName() +
                        " &cError loading enchantment with ID " + enchantID + "!"));
            }
        }
        if (EnchantmentsPlugin.getInstance().getConfiguration().loadAllEcoEnchants)
            eco += EcoUtils.loadAllEcoEnchants();
        if (EnchantmentsPlugin.getInstance().getConfiguration().loadAllAdvancedEnchants)
            ad += AEUtils.loadAllAEEnchants();
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + EnchantmentsPlugin.getInstance().getDescription().getName() +
                " &6Successfully loaded " + van + " Vanilla Enchantments!"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + EnchantmentsPlugin.getInstance().getDescription().getName() +
                " &dSuccessfully loaded " + ad + " enchantments from AdvancedEnchantments!"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + EnchantmentsPlugin.getInstance().getDescription().getName() +
                " &bSuccessfully loaded " + eco + " enchantments from EcoEnchants!"));
    }
    
    public List<HyperEnchant> getAvailableEnchantments(ItemStack itemStack) {
        if (itemStack == null) return null;
        return enchantments.values().stream().filter(enchant -> enchant.itemCanBeEnchanted(itemStack)).collect(Collectors.toList());
    }
    
    
    public HyperEnchant getEnchantmentByID(String enchantmentID) {
        return enchantments.getOrDefault(enchantmentID, null);
    }
    
    public Map<HyperEnchant, Integer> getItemEnchantments(ItemStack itemStack) {
        Map<HyperEnchant, Integer> hyperEnchants = new HashMap<>();
        for (HyperEnchant hyperEnchant : enchantments.values()) {
            int level = hyperEnchant.getItemLevel(itemStack);
            if (level > 0)
                hyperEnchants.put(hyperEnchant, level);
        }
        return hyperEnchants;
    }
    
    
}
