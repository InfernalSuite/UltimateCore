package mc.ultimatecore.anvil.commands;

import mc.ultimatecore.anvil.HyperAnvil;
import mc.ultimatecore.anvil.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hyperanvil.reload", true, "/Hyperrunes reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperAnvil.getInstance().reloadConfigs();
            cs.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperAnvil.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperAnvil.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
