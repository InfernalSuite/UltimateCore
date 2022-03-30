package com.infernalsuite.ultimatecore.alchemy.commands;

import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.objects.AlchemyRecipe;
import com.infernalsuite.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DelRecipeCommand extends Command {

    public DelRecipeCommand() {
        super(Collections.singletonList("delrecipe"), "Delete a Recipe", "hyperalchemy.delrecipe", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String name = args[1];
            AlchemyRecipe craftingRecipe = HyperAlchemy.getInstance().getBrewingRecipes().getRecipeByName(name);
            if (craftingRecipe != null){
                HyperAlchemy.getInstance().getBrewingRecipes().removeRecipe(craftingRecipe);
                p.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("recipeRemoved").replace("%recipe_name%", craftingRecipe.getName()).replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
                return true;
            }else{
                p.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("notCreated").replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
            }

        }else{
            p.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
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
            return new ArrayList<>(HyperAlchemy.getInstance().getBrewingRecipes().getRecipes().keySet());
        return null;
    }

}
