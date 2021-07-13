package mc.ultimatecore.collections.commands;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.gui.SubMenusGUI;
import mc.ultimatecore.collections.objects.Category;
import mc.ultimatecore.collections.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SubMenusCommand extends Command {
    
    public SubMenusCommand() {
        super(Collections.singletonList("submenu"), "Opens collections submenu", "hypercollections.submenu", true, "/Collections submenu [Category]");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length > 1 && args.length < 4) {
            try {
                Optional<Category> category = HyperCollections.getInstance().getCollections().getCategory(args[1]);
                if (!category.isPresent()) return;
                p.openInventory(new SubMenusGUI(p.getUniqueId(), category.get()).getInventory());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                p.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
            }
            
        } else {
            p.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
}
