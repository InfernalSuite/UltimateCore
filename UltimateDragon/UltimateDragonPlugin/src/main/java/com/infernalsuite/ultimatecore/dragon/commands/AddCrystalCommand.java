package com.infernalsuite.ultimatecore.dragon.commands;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import com.infernalsuite.ultimatecore.dragon.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;


public class AddCrystalCommand extends Command {

    public AddCrystalCommand() {
        super(Collections.singletonList("addcrystal"), "Add Dragon's Crystal", "mc.ultimatecore.dragon.addcrystal", true, "/HyperDragons addcrystal");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 1){
            Location location = p.getLocation().getBlock().getLocation();
            HyperDragons.getInstance().getDragonManager().getDragonStructure().getCrystals().add(location);
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("crystalSet").replace("%location%", Utils.getFormattedLocation(location)).replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }else{
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
