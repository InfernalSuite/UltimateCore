package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.RecipeBookGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RecipeBookCommand extends Command {

    public RecipeBookCommand() {
        super(Collections.singletonList("recipebook"), "Opens the recipe book menu", "hypercrafting.recipebook", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        new RecipeBookGUI(HyperCrafting.getInstance()).openInventory(p);
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
