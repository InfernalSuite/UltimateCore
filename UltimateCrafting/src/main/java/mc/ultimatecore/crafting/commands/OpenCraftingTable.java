package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.playerdata.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class OpenCraftingTable extends Command {

    public OpenCraftingTable() {
        super(Collections.singletonList("mainmenu"), "Opens the crafting main menu", "hypercrafting.mainmenu", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        user.getMainMenu();
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
