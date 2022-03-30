package com.infernalsuite.ultimatecore.farm.commands;

import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.farm.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all Config Files", "ultimatefarm.reload", false);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            HyperRegions.getInstance().reloadConfigs();
            sender.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().reloaded.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
            return true;
        } else {
            sender.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().invalidArguments.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
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
