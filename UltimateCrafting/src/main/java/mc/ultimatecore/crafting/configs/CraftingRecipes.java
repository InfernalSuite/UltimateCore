package mc.ultimatecore.crafting.configs;

import lombok.Getter;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class CraftingRecipes extends YAMLFile{

    @Getter
    private List<CraftingRecipe> recipes;

    public CraftingRecipes(HyperCrafting hyperCrafting, String name, boolean loadDefaults) {
        super(hyperCrafting, name, loadDefaults);
        loadDefaults();
    }

    @Override
    public void reload(){
        if(super.save()){
            super.reload();
            loadDefaults();
        }
    }


    public void addRecipe(CraftingRecipe craftingRecipe){
        if(craftingRecipe == null) return;
        recipes.add(craftingRecipe);
    }

    public boolean removeRecipe(CraftingRecipe craftingRecipe){
        if(craftingRecipe == null) return false;
        if(recipes.contains(craftingRecipe)){
            craftingRecipe.remove();
            recipes.remove(craftingRecipe);
            return true;
        }else{
            return false;
        }
    }


    public Optional<CraftingRecipe> getRecipeByName(String name){
        return recipes.stream().filter(craftingRecipe -> craftingRecipe.getName().equals(name)).findFirst();
    }

    public Optional<CraftingRecipe> matchRecipe(Player player, HashMap<Integer, ItemStack> itemStackMap){
        return recipes.stream()
                .filter(recipe -> recipe.hasPermission(player))
                .collect(Collectors.toList())
                .stream()
                .filter(craftingRecipe -> craftingRecipe.isSimilar(itemStackMap))
                .findFirst();
    }

    public boolean isCreated(String name){
        return recipes.stream().anyMatch(craftingRecipe -> craftingRecipe.getName().equals(name));
    }

    public void deleteRecipe(CraftingRecipe craftingRecipe){
        try {
            if(getConfig().contains("recipes."+craftingRecipe.getName()))
                getConfig().set("recipes."+craftingRecipe.getName(), null);
            save();
        }catch (Exception ignored){

        }
    }

    public boolean save(){
        try{
            ConfigurationSection section = getConfig().getConfigurationSection("recipes");
            if(section != null) {
                for (String key : section.getKeys(false))
                    getConfig().set("recipes."+key, null);
                super.save();
            }
            for(CraftingRecipe craftingRecipe : recipes){
                getConfig().set("recipes."+craftingRecipe.getName()+".result", craftingRecipe.getResult());
                for(Integer i : craftingRecipe.getRecipeItems().keySet()){
                    getConfig().set("recipes."+craftingRecipe.getName()+"."+i, craftingRecipe.getRecipeItems().get(i));
                }
                getConfig().set("recipes."+craftingRecipe.getName()+".isOverride", craftingRecipe.isOverrideRecipe());
                getConfig().set("recipes."+craftingRecipe.getName()+".permission", craftingRecipe.getPermission() == null ? "" : craftingRecipe.getPermission());
                getConfig().set("recipes."+craftingRecipe.getName()+".slot", craftingRecipe.getSlot());
                getConfig().set("recipes."+craftingRecipe.getName()+".page", craftingRecipe.getPage());
                getConfig().set("recipes."+craftingRecipe.getName()+".category", craftingRecipe.getCategory() == null ? "" : craftingRecipe.getCategory());
            }
            super.save();
        }catch(Exception ignored){
        }
        return true;
    }


    public void loadDefaults(){
        recipes = new ArrayList<>();
        try{
            ConfigurationSection section = getConfig().getConfigurationSection("recipes");
            if(section == null) return;
            for(String recipeName : section.getKeys(false)){
                Map<Integer, ItemStack> recipeItems = new HashMap<>();
                ItemStack result = null;
                String permission = "";
                int page = 1;
                int slot = 1;
                boolean isOverride = false;
                String category = "";
                for(String key : getConfig().getConfigurationSection("recipes."+recipeName).getKeys(false)){
                    if(key.contains("result")) {
                        result = getConfig().getItemStack("recipes." + recipeName + "." + key);
                    }else if(key.contains("permission")){
                        permission = getConfig().getString("recipes." + recipeName + "." + key);
                    }else if(key.contains("page")){
                        page = getConfig().getInt("recipes." + recipeName + "." + key);
                    }else if(key.contains("slot")){
                        slot = getConfig().getInt("recipes." + recipeName + "." + key);
                    }else if(key.contains("category")) {
                        category = getConfig().getString("recipes." + recipeName + "." + key);
                    }else if(key.contains("isOverride")){
                            category = getConfig().getString("recipes." + recipeName + "." + key);
                    }else{
                        recipeItems.put(Integer.valueOf(key), getConfig().getItemStack("recipes."+recipeName+"."+key));
                    }
                }
                CraftingRecipe craftingRecipe = new CraftingRecipe(recipeName, result, permission, slot, page, category, isOverride);
                craftingRecipe.setRecipeItems(recipeItems);
                recipes.add(craftingRecipe);
            }
        }catch (Exception ignored){
        }
    }

    public Optional<CraftingRecipe> getRecipeByItem(ItemStack itemStack){
        if(itemStack == null) return Optional.empty();
        return recipes.stream().filter(craftingRecipe -> craftingRecipe.isSimilar(itemStack)).findFirst();
    }

}