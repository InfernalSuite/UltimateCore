package mc.ultimatecore.souls.commands;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {
    
    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hypersouls.reload", false, "/HyperSouls reload");
    }
    
    @Override
    public void execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        if (args.length == 1) {
            HyperSouls.getInstance().reloadConfigs();
            p.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
        } else {
            p.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
