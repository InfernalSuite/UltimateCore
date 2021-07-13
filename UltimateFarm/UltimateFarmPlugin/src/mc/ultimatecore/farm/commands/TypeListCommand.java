package mc.ultimatecore.farm.commands;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TypeListCommand extends Command {

    public TypeListCommand() {
        super(Collections.singletonList("typelist"), "To see all types of regions", "hyperregions.typeregions", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            for (String line : HyperRegions.getInstance().getMessages().typeList) {
                if (line.contains("%region_type%")) {
                    HyperRegions.getInstance().getFarmManager().hyperRegions.forEach((key, regions) -> p.sendMessage(Utils.color(line.replace("%region_type%", key))));
                } else {
                    p.sendMessage(Utils.color(line));
                }
            }
        } else {
            p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().invalidArguments.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
        }
        return false;
    }

    @Override
    public void admin(CommandSender sender, String[] args) {
        execute(sender, args);
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
