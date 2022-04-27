package mc.ultimatecore.crafting.objects;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.recipeeditor.IndividualRecipeGUI;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CraftingRecipe implements Recipe {

    private final String name;

    private Map<Integer, ItemStack> recipeItems;

    private final ItemStack result;

    private String permission;

    private IndividualRecipeGUI individualRecipeGUI;

    private int slot;

    private int page;

    private String category;

    private boolean overrideRecipe;

    public CraftingRecipe(String name, ItemStack result){
        this.name = name;
        this.recipeItems = new HashMap<>();
        this.result = result;
        this.permission = "";
        this.overrideRecipe = false;
        individualRecipeGUI = new IndividualRecipeGUI(this);
    }

    public CraftingRecipe(String name, ItemStack result, String permission, int slot, int page, String category, boolean overrideRecipe){
        this.name = name;
        this.result = result;
        this.permission = permission;
        this.slot = slot;
        this.page = page;
        this.category = category;
        this.overrideRecipe = overrideRecipe;
        individualRecipeGUI = new IndividualRecipeGUI(this);

    }

    public boolean isSimilar(Map<Integer, ItemStack> anotherRecipe){
        int size = 0;

        for(Integer index : recipeItems.keySet()){

            if(!anotherRecipe.containsKey(index)) return false;

            ItemStack itemStack = recipeItems.get(index);
            ItemStack anotherItem = anotherRecipe.get(index);

            if(itemStack.isSimilar(anotherItem) && anotherItem.getAmount() >= itemStack.getAmount())
                size++;

        }
        return size == recipeItems.size() && anotherRecipe.size() == recipeItems.size();
    }

    public boolean isSimilar(ItemStack itemStack){
        return itemStack.isSimilar(getResult());
    }

    public boolean hasPermission(Player player){
        return permission == null || permission.equals("") || player.hasPermission(permission);
    }

    public int getMaxItemQuantity(){
        int quantity = 0;
        for (Integer integer : recipeItems.keySet()){
            ItemStack item = recipeItems.get(integer);
            if(Utils.itemIsNull(item)) continue;
            if(item.getAmount() > quantity)
                quantity = item.getAmount();
        }
        return quantity;
    }

    @Override
    @NotNull
    public ItemStack getResult() {
        return result.clone();
    }

    public void remove(){
        HyperCrafting.getInstance().getCraftingRecipes().deleteRecipe(this);
    }



}
