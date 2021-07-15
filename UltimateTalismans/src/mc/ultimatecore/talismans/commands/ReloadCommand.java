package mc.ultimatecore.talismans.commands;


import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hypertalismans.reload", false, "/Talismans reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperTalismans.getInstance().reloadConfigs();
            cs.sendMessage(StringUtils.color(HyperTalismans.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperTalismans.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperTalismans.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperTalismans.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
