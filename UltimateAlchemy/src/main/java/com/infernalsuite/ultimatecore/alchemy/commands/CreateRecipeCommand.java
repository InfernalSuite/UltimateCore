package com.infernalsuite.ultimatecore.alchemy.commands;

import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.objects.AlchemyRecipe;
import com.infernalsuite.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CreateRecipeCommand extends Command {

    public CreateRecipeCommand() {
        super(Collections.singletonList("newrecipe"), "Create new Recipe", "hyperalchemy.newrecipe", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String name = args[1];
            if (HyperAlchemy.getInstance().getBrewingRecipes().getRecipeByName(name) == null){
                AlchemyRecipe craftingRecipe = new AlchemyRecipe(name);
                p.openInventory(craftingRecipe.getRecipeCreatorGUI().getInventory());
                return true;
            }else{
                p.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("alreadyCreated").replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
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
        return null;
    }

}
