package mc.ultimatecore.collections.commands;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {
    
    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hypercollections.reload", false, "/Collections reload");
    }
    
    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperCollections.getInstance().reloadConfigs();
            cs.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
        } else {
            cs.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
