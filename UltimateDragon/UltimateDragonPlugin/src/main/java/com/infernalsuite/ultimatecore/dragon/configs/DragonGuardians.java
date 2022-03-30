package com.infernalsuite.ultimatecore.dragon.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.dragon.objects.guardian.DragonGuardian;
import com.infernalsuite.ultimatecore.dragon.objects.guardian.GuardianArmor;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IGuardian;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class DragonGuardians extends YAMLFile {
    @Getter
    private Map<String, DragonGuardian> guardianMap;

    public DragonGuardians(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        guardianMap = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection("guardians");
        if(section != null)
            for(String key : section.getKeys(false)){
                try {
                    String displayName = getConfig().getString("guardians."+key+".displayName", null);
                    String entity = getConfig().getString("guardians."+key+".entity", null);
                    double damage = getConfig().getDouble("guardians."+key+".damage", 0);
                    double health = getConfig().getDouble("guardians."+key+".health", 0);
                    ItemStack boots = getConfig().getItemStack("guardians."+key+".boots", null);
                    ItemStack leggings = getConfig().getItemStack("guardians."+key+".leggings", null);
                    ItemStack chestplate = getConfig().getItemStack("guardians."+key+".chestplate", null);
                    ItemStack helmet = getConfig().getItemStack("guardians."+key+".helmet", null);
                    GuardianArmor guardianArmor = new GuardianArmor(helmet, chestplate, leggings, boots);
                    DragonGuardian dragonGuardian = new DragonGuardian(key, displayName, health, damage, guardianArmor, entity);
                    guardianMap.put(key, dragonGuardian);
                }catch (Exception e){
                    Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[UltimateCore-Dragon] &cError loading guardian "+key+"!"));
                }
            }

    }

    public void save(){
        ConfigurationSection section = getConfig().getConfigurationSection("guardians");
        if(section != null)
            section.getKeys(false).forEach(key -> getConfig().set("guardians."+key, null));
        super.save();
        for(DragonGuardian iGuardian : guardianMap.values()){
            getConfig().set("guardians."+iGuardian.getID()+".displayName", iGuardian.getDisplayName());
            getConfig().set("guardians."+iGuardian.getID()+".entity", iGuardian.getEntity());
            getConfig().set("guardians."+iGuardian.getID()+".damage", iGuardian.getDamage());
            getConfig().set("guardians."+iGuardian.getID()+".health", iGuardian.getHealth());
            getConfig().set("guardians."+iGuardian.getID()+".boots", iGuardian.getGuardianArmor().getBoots());
            getConfig().set("guardians."+iGuardian.getID()+".leggings", iGuardian.getGuardianArmor().getLeggings());
            getConfig().set("guardians."+iGuardian.getID()+".chestplate", iGuardian.getGuardianArmor().getChestplate());
            getConfig().set("guardians."+iGuardian.getID()+".helmet", iGuardian.getGuardianArmor().getHelmet());
        }
        super.save();
    }

    public IGuardian getGuardian(String name){
        return guardianMap.getOrDefault(name, null);
    }

    public void addGuardian(String name){
        guardianMap.put(name, new DragonGuardian(name, new GuardianArmor()));
    }

    public void remove(String name){
        guardianMap.remove(name);
    }

    private Object getValue(String path){
        if(getConfig().contains(path) && getConfig().get(path) != null)
            return getConfig().get(path);
        return null;
    }
}