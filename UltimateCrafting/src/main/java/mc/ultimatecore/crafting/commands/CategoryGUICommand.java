package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.CategoryRecipeGUI;
import mc.ultimatecore.crafting.objects.Category;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryGUICommand extends Command {

    private final HyperCrafting plugin;

    public CategoryGUICommand(HyperCrafting plugin) {
        super(Collections.singletonList("category"), "Opens specific category gui", "hypercrafting.category", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            Category category = this.plugin.getCategories().getCategory(args[1]);
            if(category != null)
                new CategoryRecipeGUI(this.plugin, category, 1).openInventory(p);
            else
                p.sendMessage(Utils.color(this.plugin.getMessages().getMessage("invalidCategory").replace("%prefix%", this.plugin.getConfiguration().prefix)));
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
            return this.plugin.getCategories().getAllCategories().stream().map(Category::getKey).collect(Collectors.toList());
        return null;
    }

}
