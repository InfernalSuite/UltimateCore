package com.infernalsuite.ultimatecore.alchemy.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.objects.AlchemyRecipe;
import com.infernalsuite.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BrewingRecipes extends YAMLFile{

    @Getter
    private final HashMap<String, AlchemyRecipe> recipes = new HashMap<>();

    public BrewingRecipes(HyperAlchemy hyperCrafting, String name) {
        super(hyperCrafting, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadDefaults();
    }

    @Override
    public void reload(){
        save();
        getConfig().reload();
        loadDefaults();
    }

    public void save(){
        try{
            getConfig().get().set("recipes", null);
            int countdown = 0;
            for(String recipeName : recipes.keySet()){
                AlchemyRecipe alchemyRecipe = recipes.get(recipeName);
                getConfig().get().set("recipes."+recipeName+".inputItem", alchemyRecipe.getInputItem());
                getConfig().get().set("recipes."+recipeName+".outputItem", alchemyRecipe.getOutputItem());
                getConfig().get().set("recipes."+recipeName+".permission", alchemyRecipe.getPermission());
                getConfig().get().set("recipes."+recipeName+".fuelItem", alchemyRecipe.getFuelItem());
                getConfig().get().set("recipes."+recipeName+".time", alchemyRecipe.getTime());
                countdown++;
            }
            if(countdown > 0)
                Bukkit.getConsoleSender().sendMessage(StringUtils.color("&a[HyperAlchemy] Successfully saved "+countdown+" recipes!"));
            getConfig().save();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadDefaults(){
        try{
            ConfigurationSection section = getConfig().get().getConfigurationSection("recipes");
            if(section == null) return;
            for(String recipeName : section.getKeys(false)){
                ItemStack inputItem = getConfig().get().getItemStack("recipes."+recipeName+".inputItem");

                ItemStack outputItem = getConfig().get().getItemStack("recipes."+recipeName+".outputItem");

                ItemStack fuelItem = getConfig().get().getItemStack("recipes."+recipeName+".fuelItem");

                String permission = getConfig().get().getString("recipes."+recipeName+".permission");

                int time = getConfig().get().getInt("recipes."+recipeName+".time");

                AlchemyRecipe alchemyRecipe = new AlchemyRecipe(recipeName, inputItem, fuelItem, outputItem, permission, time, null, null);

                recipes.put(recipeName, alchemyRecipe);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addRecipe(AlchemyRecipe alchemyRecipe){
        recipes.put(alchemyRecipe.getName(), alchemyRecipe);
    }

    public void removeRecipe(AlchemyRecipe alchemyRecipe){
        recipes.remove(alchemyRecipe.getName());
    }

    public AlchemyRecipe getRecipeByName(String name){
        if(recipes.containsKey(name))
            return recipes.get(name);
        return null;
    }


}
