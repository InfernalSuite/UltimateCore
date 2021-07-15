package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SilentMoneyCommand extends HyperCommand {

    public SilentMoneyCommand() {
        super(Collections.singletonList("silentmoney"), "Give money without chat message", "hyperskills.silentmoney", false, "/Skills silentmoney [player] [amount]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            try {
                double quantity = Double.parseDouble(args[2]);
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    HyperSkills.getInstance().getAddonsManager().getEconomyPlugin().deposit(player, quantity);
                }else {
                    sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }
            } catch (IllegalArgumentException e) {
                sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
            }

        } else {
            sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
