package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class GetItemCommand extends HyperCommand {

    public GetItemCommand() {
        super(Collections.singletonList("giveitem"), "Give specials items", "hyperskills.giveitem", false, "/Skills giveitem [player] [name]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 3){
            try {
                Player player = Bukkit.getPlayer(args[1]);
                String name = args[2];
                if(!HyperSkills.getInstance().getUltimateItems().ultimateItems.containsKey(name)){
                    if(sender instanceof Player)
                        sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidItem").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }else{
                    if (player != null) {
                        UltimateItem nbtItem = HyperSkills.getInstance().getUltimateItems().ultimateItems.get(name);
                        player.getInventory().addItem(nbtItem.getItem());
                    } else{
                        if(sender instanceof Player)
                            sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                    }
                }
            }catch (Exception e){ }
        } else {
            if(sender instanceof Player)
                sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
