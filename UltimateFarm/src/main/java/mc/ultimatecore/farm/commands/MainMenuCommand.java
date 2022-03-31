package mc.ultimatecore.farm.commands;

import mc.ultimatecore.farm.gui.AllCropsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MainMenuCommand extends Command {

    public MainMenuCommand() {
        super(Collections.singletonList("mainmenu"), "Opens the farm main menu", "ultimatefarm.mainmenu", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(new AllCropsGUI(1).getInventory());
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
