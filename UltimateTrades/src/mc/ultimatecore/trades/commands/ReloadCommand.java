package mc.ultimatecore.trades.commands;

import mc.ultimatecore.trades.HyperTrades;
import mc.ultimatecore.trades.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hypertrades.reload", false, "/Hypertrades reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperTrades.getInstance().reloadConfigs();
            cs.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
