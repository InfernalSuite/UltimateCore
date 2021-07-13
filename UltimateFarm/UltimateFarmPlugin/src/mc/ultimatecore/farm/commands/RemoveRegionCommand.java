package mc.ultimatecore.farm.commands;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.objects.HyperRegion;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoveRegionCommand extends Command {

    public RemoveRegionCommand() {
        super(Collections.singletonList("delregion"), "Remove a region", "hyperregions.delregion", true);
    }


    /*
    /Hyperregions delregion <region> <type>
     */

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 3) {
            p.sendMessage(Utils.color("%prefix% &aSince version 4.1.12 Plugin can be configured only through config file, this allow you to load regions easily with /HyperRegions reload.".replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
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
        if (args.length == 2) {
            return new ArrayList<>(HyperRegions.getInstance().getFarmManager().hyperRegions.keySet());
        } else if (args.length == 3) {
            String key = args[1].toUpperCase();
            if (!HyperRegions.getInstance().getFarmManager().hyperRegions.containsKey(key))
                return Collections.emptyList();
            HyperRegion farmRegion = HyperRegions.getInstance().getFarmManager().hyperRegions.get(key);
            return new ArrayList<>(farmRegion.getRegions());
        }
        return null;
    }

}
