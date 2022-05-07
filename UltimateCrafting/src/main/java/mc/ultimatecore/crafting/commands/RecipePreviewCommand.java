package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.RecipePreviewGUI;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecipePreviewCommand extends Command {

    private final HyperCrafting plugin;

    public RecipePreviewCommand(HyperCrafting plugin) {
        super(Collections.singletonList("preview"), "View recipe preview", "hypercrafting.preview", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            Optional<CraftingRecipe> craftingRecipe = this.plugin.getCraftingRecipes().getRecipeByName(args[1]);
            if(!craftingRecipe.isPresent()) return false;
            new RecipePreviewGUI(this.plugin, craftingRecipe.get()).openInventory(p);
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
