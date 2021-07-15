package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PerkListCommand extends HyperCommand {

    public PerkListCommand() {
        super(Collections.singletonList("perklist"), "To view all available perks", "hyperskills.perklist", true, "/Skills perklist");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
                p.sendMessage(StringUtils.color("&6&lPerk List"));
                for(Perk perk : Perk.values())
                    p.sendMessage(StringUtils.color("&7- &e"+perk.getName()));
                p.sendMessage("");
        } else {
            p.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
}
