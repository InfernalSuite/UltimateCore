package mc.ultimatecore.dragon.commands;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.dragon.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;


public class SetSpawnCommand extends Command {

    public SetSpawnCommand() {
        super(Collections.singletonList("setspawn"), "Set Dragon's spawn", "hyperdragon.setspawn", true, "/HyperDragons setspawn");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 1){
            Location location = p.getLocation();
            HyperDragons.getInstance().getDragonManager().getDragonStructure().setSpawnLocation(location);
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("spawnSet").replace("%location%", Utils.getFormattedLocation(location)).replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }else{
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
