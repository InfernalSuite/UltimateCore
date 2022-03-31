package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.playerdata.User;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class CreateRecipeCommand extends Command {

    public CreateRecipeCommand() {
        super(Collections.singletonList("newrecipe"), "Create new Recipe", "hypercrafting.newrecipe", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if(args.length == 2){
            ItemStack result = p.getItemInHand();
            if (result != null && result.getType() != Material.AIR) {
                String name = args[1];
                CraftingRecipe craftingRecipe = new CraftingRecipe(name, result.clone());
                if (!HyperCrafting.getInstance().getCraftingRecipes().isCreated(name)){
                    p.openInventory(user.getRecipeCreatorGUI(craftingRecipe).getInventory());
                    return true;
                }else{
                    p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("alreadyCreated").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
                }
            }else{
                p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("itemInHand").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
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
        return null;
    }

}
