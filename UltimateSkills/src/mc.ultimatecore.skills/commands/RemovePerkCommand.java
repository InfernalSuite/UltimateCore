package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RemovePerkCommand extends HyperCommand {

    public RemovePerkCommand() {
        super(Collections.singletonList("removeperk"), "Remove player's perk", "hyperskills.removeperk", false, "/Skills removeperk [player] [perk] [quantity]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 4) {
            try {
                Perk perk = Perk.valueOf(args[2]);
                Double quantity = Double.valueOf(args[3]);
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    HyperSkills.getInstance().getApi().removePerk(player.getUniqueId(), perk, quantity);
                    if(sender instanceof Player)
                        sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("removedPerk")
                            .replace("%player%", player.getName())
                            .replace("%perk%", perk.toString())
                            .replace("%quantity%", String.valueOf(quantity))
                            .replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }else {
                    if(sender instanceof Player)
                        sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }
            } catch (IllegalArgumentException e) {
                if(sender instanceof Player)
                    sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
            }

        } else {
            sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length == 3)
            return Arrays.stream(Perk.values()).map(Perk::getName).collect(Collectors.toList());
        return null;
    }

}
