package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DelRecipeCommand extends Command {

    public DelRecipeCommand() {
        super(Collections.singletonList("delrecipe"), "Delete a Recipe", "hypercrafting.delrecipe", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String name = args[1];
            Optional<CraftingRecipe> craftingRecipe = HyperCrafting.getInstance().getCraftingRecipes().getRecipeByName(name);
            if (craftingRecipe.isPresent()){
                if(HyperCrafting.getInstance().getCraftingRecipes().removeRecipe(craftingRecipe.get())){
                    p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("recipeRemoved").replace("%recipe_name%", craftingRecipe.get().getName()).replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
                    return true;
                }
            }else{
                p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("notCreated").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
            }

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
