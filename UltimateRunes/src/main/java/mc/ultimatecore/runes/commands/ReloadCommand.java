package mc.ultimatecore.runes.commands;

import mc.ultimatecore.runes.HyperRunes;
import mc.ultimatecore.runes.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hyperrunes.reload", true, "/Hyperrunes reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperRunes.getInstance().reloadConfigs();
            cs.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
