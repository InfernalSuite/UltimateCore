package mc.ultimatecore.menu.commands;

import mc.ultimatecore.menu.HyperCore;
import mc.ultimatecore.menu.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hypercore.reload", false, "/HyperCore reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperCore.getInstance().reloadConfigs();
            cs.sendMessage(StringUtils.color(HyperCore.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperCore.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperCore.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCore.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
