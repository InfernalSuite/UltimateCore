package com.infernalsuite.ultimatecore.farm.commands;

import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.farm.objects.HyperRegion;
import com.infernalsuite.ultimatecore.farm.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RegionListCommand extends Command {

    public RegionListCommand() {
        super(Collections.singletonList("regionlist"), "Show all regions availables", "ultimatefarm.regionlist", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            for (String key : HyperRegions.getInstance().getFarmManager().hyperRegions.keySet()) {
                regionList(key, p);
            }
        } else if (args.length == 2) {
            String key = args[1].toUpperCase();
            if (HyperRegions.getInstance().getFarmManager().hyperRegions.containsKey(key)) {
                regionList(key, p);
            } else {
                p.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().regionTypeNotExist.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
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

    private void regionList(String key, Player player) {
        HyperRegion farmRegion = HyperRegions.getInstance().getFarmManager().hyperRegions.get(key);
        for (String line : HyperRegions.getInstance().getMessages().regionList) {
            if (line.contains("%region%")) {
                for (String regionName : farmRegion.getRegions())
                    player.sendMessage(Utils.color(line.replace("%region%", regionName)));
            } else {
                player.sendMessage(Utils.color(line.replace("%region_type%", key)));
            }
        }
    }

}
