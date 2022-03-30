package com.infernalsuite.ultimatecore.crafting.commands;

import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.gui.RecipePreviewGUI;
import com.infernalsuite.ultimatecore.crafting.objects.CraftingRecipe;
import com.infernalsuite.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecipePreviewCommand extends Command {

    public RecipePreviewCommand() {
        super(Collections.singletonList("preview"), "View recipe preview", "hypercrafting.preview", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            Optional<CraftingRecipe> craftingRecipe = HyperCrafting.getInstance().getCraftingRecipes().getRecipeByName(args[1]);
            if(!craftingRecipe.isPresent()) return false;
            new RecipePreviewGUI(HyperCrafting.getInstance(), craftingRecipe.get()).openInventory(p);
        }else{
            p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
        }
        return false;
    }

    @Override
    public void admin(CommandSender sender, String[] args) {
        execute(sender, args);
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        if(args.length == 2)
            return HyperCrafting.getInstance().getCraftingRecipes().getRecipes().stream().map(CraftingRecipe::getName).collect(Collectors.toList());
        return null;
    }

}
