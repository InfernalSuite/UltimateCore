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

    private final HyperCrafting plugin;

    public DelRecipeCommand(HyperCrafting plugin) {
        super(Collections.singletonList("delrecipe"), "Delete a Recipe", "hypercrafting.delrecipe", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String name = args[1];
            Optional<CraftingRecipe> craftingRecipe = this.plugin.getCraftingRecipes().getRecipeByName(name);
            if (craftingRecipe.isPresent()){
                if(this.plugin.getCraftingRecipes().removeRecipe(craftingRecipe.get())){
                    p.sendMessage(Utils.color(this.plugin.getMessages().getMessage("recipeRemoved").replace("%recipe_name%", craftingRecipe.get().getName()).replace("%prefix%", this.plugin.getConfiguration().prefix)));
                    return true;
                }
            }else{
                p.sendMessage(Utils.color(this.plugin.getMessages().getMessage("notCreated").replace("%prefix%", this.plugin.getConfiguration().prefix)));
            }

        }else{
            p.sendMessage(Utils.color(this.plugin.getMessages().getMessage("invalidArguments").replace("%prefix%", this.plugin.getConfiguration().prefix)));
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
            return this.plugin.getCraftingRecipes().getRecipes().stream().map(CraftingRecipe::getName).collect(Collectors.toList());
        return null;
    }

}
