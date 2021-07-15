package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.gui.recipeeditor.AllRecipesGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class EditorMenuCommand extends Command {

    public EditorMenuCommand() {
        super(Collections.singletonList("editormenu"), "Open recipes editor menu", "hypercrafting.editormenu", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 1)
            p.openInventory(new AllRecipesGUI(1).getInventory());
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
