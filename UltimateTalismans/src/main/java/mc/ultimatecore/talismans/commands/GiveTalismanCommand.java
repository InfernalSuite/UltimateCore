package mc.ultimatecore.talismans.commands;

import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.objects.Talisman;
import mc.ultimatecore.talismans.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveTalismanCommand extends Command{

    public GiveTalismanCommand() {
        super(Collections.singletonList("give"), "Create new Item", "hypertalismans.give", false, "/Talismans give <player> <talisman>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 3){
            Player player = Bukkit.getPlayer(args[1]);
            if(player != null){
                String name = args[2];
                if(HyperTalismans.getInstance().getTalismans().getTalismans().containsKey(name)){
                    Talisman talisman = HyperTalismans.getInstance().getTalismans().getTalismans().get(name);
                    player.getInventory().addItem(talisman.getItem());
                }else{
                    sender.sendMessage(StringUtils.color(HyperTalismans.getInstance().getMessages().getMessage("invalidTalisman").replace("%prefix%", HyperTalismans.getInstance().getConfiguration().prefix)));
                }
            } else {
                sender.sendMessage(StringUtils.color(HyperTalismans.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperTalismans.getInstance().getConfiguration().prefix)));
            }
        }else{
            sender.sendMessage(StringUtils.color(HyperTalismans.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperTalismans.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length == 3)
            return new ArrayList<>(HyperTalismans.getInstance().getTalismans().getTalismans().keySet());
        return null;
    }
}
