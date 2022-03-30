package com.infernalsuite.ultimatecore.alchemy.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.infernalsuite.ultimatecore.alchemy.gui.RecipeEditorGUI;
import com.infernalsuite.ultimatecore.alchemy.gui.RecipePreviewGUI;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AlchemyRecipe {

    private final String name;

    private ItemStack inputItem;

    private ItemStack fuelItem;

    private ItemStack outputItem;

    private String permission;

    private int time;

    private RecipeEditorGUI recipeCreatorGUI;

    private RecipePreviewGUI recipePreviewGUI;

    public RecipeEditorGUI getRecipeCreatorGUI(){
        if(recipeCreatorGUI == null) recipeCreatorGUI = new RecipeEditorGUI(this);
        return recipeCreatorGUI;
    }

    public RecipePreviewGUI getRecipePreviewGUI(){
        if(recipePreviewGUI == null) recipePreviewGUI = new RecipePreviewGUI(this);
        return recipePreviewGUI;
    }
}
