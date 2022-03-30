package mc.ultimatecore.talismans.commands;


import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TalismanBagCommand extends Command {

    public TalismanBagCommand() {
        super(Collections.singletonList("bag"), "Open talismans bag", "hypertalismans.bag", true, "/Talismans bag");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        if (args.length == 1) {
            player.openInventory(HyperTalismans.getInstance().getUserManager().getGUI(player.getUniqueId()).getInventory());
        }else{
            cs.sendMessage(StringUtils.color(HyperTalismans.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperTalismans.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
