package mc.ultimatecore.dragon.commands;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;


public class KillDragonCommand extends Command {

    public KillDragonCommand() {
        super(Collections.singletonList("kill"), "Kill Ender Dragon", "hyperdragon.give", false, "/HyperDragons kill");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1){
            if(HyperDragons.getInstance().getDragonManager().isActive())
                HyperDragons.getInstance().getDragonManager().finish();
        }else{
            sender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
