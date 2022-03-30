package mc.ultimatecore.alchemy.commands;

import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.objects.AlchemyRecipe;
import mc.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipePreviewCommand extends Command {

    public RecipePreviewCommand() {
        super(Collections.singletonList("preview"), "View recipe preview", "hyperalchemy.preview", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            AlchemyRecipe craftingRecipe = HyperAlchemy.getInstance().getBrewingRecipes().getRecipeByName(args[1]);
            if(craftingRecipe == null) return false;
            p.openInventory(craftingRecipe.getRecipePreviewGUI().getInventory());
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
