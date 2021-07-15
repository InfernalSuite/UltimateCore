package mc.ultimatecore.runes.commands;

import mc.ultimatecore.runes.HyperRunes;
import mc.ultimatecore.runes.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class GetRuneCommand extends Command {

    public GetRuneCommand() {
        super(Collections.singletonList("giverune"), "Give Rune to a player", "hyperrunes.giverune", true, "/Hyperrunes giverune [player] [name]");
    }

    /*
    /skills getitem <item> <quantity> <player>
     */

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 3){
            try {
                Player player = Bukkit.getPlayer(args[1]);
                String name = args[2];
                if(HyperRunes.getInstance().getRunes().getRuneByName(name) == null){
                    if(sender instanceof Player)
                        sender.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("invalidRune").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
                }else{
                    if (player != null) {
                        player.getInventory().addItem(HyperRunes.getInstance().getRunes().getRune(name, 1));
                    } else{
                        if(sender instanceof Player)
                            sender.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
                    }
                }
            }catch (Exception ignored){ }
        } else {
            if(sender instanceof Player)
                sender.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
