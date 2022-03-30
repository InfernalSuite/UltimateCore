package mc.ultimatecore.farm.commands;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AddGuardianCommand extends Command {

    public AddGuardianCommand() {
        super(Collections.singletonList("addguardian"), "Add a new guardian", "ultimatefarm.addguardian", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        //HyperRegions.getInstance().getGuardianHandler().addRobot(p.getLocation(), "name");
        if (args.length == 2) {
            String regionName = args[1];
            Location location = p.getLocation();
            if (HyperRegions.getInstance().getAddonsManager().getRegionPlugin() == null) {
                p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().noWorldGuard.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
                return false;
            }
            Set<String> regions = HyperRegions.getInstance().getAddonsManager().getRegionPlugin().getRegions(location);
            if (regions != null && regions.contains(regionName)) {
                if (HyperRegions.getInstance().getGuardians().addGuardian(regionName, location, true)) {
                    p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().addedGuardian.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
                } else {
                    p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().alreadyGuardian.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
                }
            } else {
                p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().mustBeInsideRegion.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
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
        if (args.length == 2)
            return new ArrayList<>(HyperRegions.getInstance().getAddonsManager().getRegionPlugin().getRegions(((Player) cs).getLocation()));
        return null;
    }

}
