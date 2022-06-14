package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class RemoveAbilitiesCommand extends HyperCommand {

    public RemoveAbilitiesCommand() {
        super(Collections.singletonList("removeability"), "Add more player's abilities", "hyperskills.removeability", false, "/Skills removeability [player] [ability] [quantity]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 4) {
            try {
                Ability ability = Ability.valueOf(args[2].toUpperCase());
                Double quantity = Double.valueOf(args[3]);
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    HyperSkills.getInstance().getApi().removeAbility(player.getUniqueId(), ability, quantity);
                    if(sender instanceof Player)
                        sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("removedAbility")
                            .replace("%player%", player.getName())
                            .replace("%ability%", ability.toString())
                            .replace("%quantity%", String.valueOf(quantity))
                            .replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }else{
                    if(sender instanceof Player) {
                        sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                    }
                }
            } catch (NumberFormatException e) {
                if(sender instanceof Player)
                    sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
            } catch (IllegalArgumentException e) {
                if(sender instanceof Player)
                    sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
            }
        } else {
            if(sender instanceof Player)
                sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> completes = new ArrayList<>();
        if(args.length == 2) {
            completes.clear();
            String arg = args[1];
            if(arg != null) {
                arg = arg.toLowerCase();
            }
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(arg == null || player.getName().toLowerCase().startsWith(arg)) {
                    completes.add(player.getName());
                }
            }
        }else if(args.length == 3) {
            completes.clear();
            String arg = args[2];
            if(arg != null) {
                arg = arg.toLowerCase();
            }
            for(Ability ability : Ability.values()) {
                if(arg == null || ability.getName().toLowerCase().startsWith(arg)) {
                    completes.add(ability.getName());
                }
            }
        }
        return completes;
    }

}
