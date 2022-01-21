package mc.ultimatecore.anvil.commands;

import mc.ultimatecore.anvil.HyperAnvil;
import mc.ultimatecore.anvil.managers.User;
import mc.ultimatecore.anvil.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MainMenuCommand extends Command {

    public MainMenuCommand() {
        super(Collections.singletonList("mainmenu"), "Open anvil mainmenu", "hyperanvil.mainmenu", true, "/HyperAnvil mainmenu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if(args.length == 1){
            p.openInventory(user.getAnvilGUI().getInventory());
        }else{
            p.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperAnvil.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
