package mc.ultimatecore.reforge.commands;

import mc.ultimatecore.reforge.HyperReforge;
import mc.ultimatecore.reforge.User;
import mc.ultimatecore.reforge.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MainMenuCommand extends Command {

    public MainMenuCommand() {
        super(Collections.singletonList("mainmenu"), "Open reforge mainmenu", "hyperreforge.mainmenu", true, "/HyperReforge mainmenu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if (args.length == 1)
            p.openInventory(user.getReforgeGUI().getInventory());
        else
            p.sendMessage(StringUtils.color(HyperReforge.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperReforge.getInstance().getConfiguration().prefix)));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
