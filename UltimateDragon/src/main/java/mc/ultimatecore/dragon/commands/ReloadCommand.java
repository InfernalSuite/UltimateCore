package mc.ultimatecore.dragon.commands;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hyperdragon.reload", false, "/HyperDragon reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperDragons.getInstance().reloadFiles();
            cs.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
