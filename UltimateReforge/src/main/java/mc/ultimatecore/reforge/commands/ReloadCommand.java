package mc.ultimatecore.reforge.commands;

import mc.ultimatecore.reforge.HyperReforge;
import mc.ultimatecore.reforge.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hyperreforge.reload", true, "/HyperReforge reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperReforge.getInstance().reloadConfigs();
            cs.sendMessage(StringUtils.color(HyperReforge.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperReforge.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperReforge.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperReforge.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
