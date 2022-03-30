package mc.ultimatecore.collections.commands;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.gui.LevelsMenu;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class LevelsMenuCommand extends Command {
    
    public LevelsMenuCommand() {
        super(Collections.singletonList("levelmenu"), "Opens collections submenu", "hypercollections.levelsmenu", true, "/Collections levelmenu [Collection]");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length > 1 && args.length < 4) {
            Collection collection = HyperCollections.getInstance().getCollections().getCollection(args[1]);
            if (collection != null)
                p.openInventory(new LevelsMenu(p.getUniqueId(), collection).getInventory());
            else
                p.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
        } else {
            p.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
