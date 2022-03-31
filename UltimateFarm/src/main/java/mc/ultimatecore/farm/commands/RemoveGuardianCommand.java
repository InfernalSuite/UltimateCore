package mc.ultimatecore.farm.commands;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.guardians.Guardian;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveGuardianCommand extends Command {

    public RemoveGuardianCommand() {
        super(Collections.singletonList("delguardian"), "Remove a guardian", "ultimatefarm.delguardian", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2) {
            String regionName = args[1];
            if (HyperRegions.getInstance().getGuardians().removeGuardian(regionName))
                p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().removedGuardian.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
            else
                p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().notGuardian.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
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
            return HyperRegions.getInstance().getGuardians().getGuardians().stream().map(Guardian::getName).collect(Collectors.toList());
        return null;
    }

}
