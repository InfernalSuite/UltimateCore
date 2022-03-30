package com.infernalsuite.ultimatecore.skills.commands;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AbilitiesListCommand extends HyperCommand {

    public AbilitiesListCommand() {
        super(Collections.singletonList("abilitylist"), "To view available abilities", "ultraskills.abilitylist", true, "/Skills abilitylist");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
                p.sendMessage(StringUtils.color("&6&lAbilities List"));
                for(Ability ability : Ability.values())
                    p.sendMessage(StringUtils.color("&7- &e"+ability.getName()));
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
