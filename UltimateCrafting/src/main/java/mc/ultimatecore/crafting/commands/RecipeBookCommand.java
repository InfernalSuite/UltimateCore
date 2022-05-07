package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.RecipeBookGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RecipeBookCommand extends Command {

    private final HyperCrafting plugin;

    public RecipeBookCommand(HyperCrafting plugin) {
        super(Collections.singletonList("recipebook"), "Opens the recipe book menu", "hypercrafting.recipebook", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        new RecipeBookGUI(this.plugin).openInventory(p);
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
