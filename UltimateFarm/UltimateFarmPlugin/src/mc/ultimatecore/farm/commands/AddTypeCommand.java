package mc.ultimatecore.farm.commands;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.objects.ChanceBlocks;
import mc.ultimatecore.farm.objects.HyperRegion;
import mc.ultimatecore.farm.objects.RegenTime;
import mc.ultimatecore.farm.objects.Textures;
import mc.ultimatecore.farm.objects.blocks.ChanceBlock;
import mc.ultimatecore.farm.objects.blocks.RegionBlock;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class AddTypeCommand extends Command {

    public AddTypeCommand() {
        super(Collections.singletonList("addtype"), "Add a new type of crop", "hyperregions.addtype", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
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
        return null;
    }

}
