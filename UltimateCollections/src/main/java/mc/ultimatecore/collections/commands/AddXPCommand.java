package mc.ultimatecore.collections.commands;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddXPCommand extends Command {
    
    public AddXPCommand() {
        super(Collections.singletonList("addxp"), "Add xp to a player", "hypercollections.addxp", false, "/Collections addxp [Player] [Collection] [Amount]");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 4) {
            Collection skill = HyperCollections.getInstance().getCollections().getCollection(args[2]);
            if (skill != null) {
                int quantity = Integer.parseInt(args[3]);
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    if (sender instanceof Player)
                        sender.sendMessage(StringUtils.color(HyperCollections.getInstance().getMessages().getMessage("addedXP")
                                                                             .replace("%player%", player.getName())
                                                                             .replace("%collection%", skill.getKey())
                                                                             .replace("%quantity%", String.valueOf(quantity))
                                                                             .replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
                    HyperCollections.getInstance().getApi().addXP(player, skill.getKey(), quantity);
                } else {
                    sender.sendMessage(StringUtils.color(HyperCollections.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
                }
            } else {
                sender.sendMessage(StringUtils.color(HyperCollections.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
            }
        } else {
            sender.sendMessage(StringUtils.color(HyperCollections.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCollections.getInstance().getConfiguration().getPrefix())));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 3)
            return new ArrayList<>(HyperCollections.getInstance().getCollections().getCollections().keySet());
        return null;
    }
}
