package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class CreateRecipeCommand extends Command {

    private HyperCrafting plugin;

    public CreateRecipeCommand(HyperCrafting plugin) {
        super(Collections.singletonList("newrecipe"), "Create new Recipe", "hypercrafting.newrecipe", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length == 2){
            ItemStack result = player.getItemInHand();
            if (result.getType() != Material.AIR) {
                String name = args[1];
                CraftingRecipe craftingRecipe = new CraftingRecipe(name, result.clone(), this.plugin);
                if (!this.plugin.getCraftingRecipes().isCreated(name)){
                    player.openInventory(this.plugin.getPlayerManager().getRecipeCreatorGUI(craftingRecipe).getInventory());
                    return true;
                }else{
                    player.sendMessage(Utils.color(this.plugin.getMessages().getMessage("alreadyCreated").replace("%prefix%", this.plugin.getConfiguration().prefix)));
                }
            }else{
                player.sendMessage(Utils.color(this.plugin.getMessages().getMessage("itemInHand").replace("%prefix%", this.plugin.getConfiguration().prefix)));
            }
        }else{
            player.sendMessage(Utils.color(this.plugin.getMessages().getMessage("invalidArguments").replace("%prefix%", this.plugin.getConfiguration().prefix)));
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
