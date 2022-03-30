package com.infernalsuite.ultimatecore.skills.commands;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AddXPCommand extends HyperCommand {

    public AddXPCommand() {
        super(Collections.singletonList("addxp"), "Add Player XP", "hyperskills.addxp", false, "/Skills addxp [player] [skill] [quantity]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 4) {
            try {
                SkillType skill = SkillType.valueOf(args[2]);
                Double quantity = Double.valueOf(args[3]);
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("setXP")
                            .replace("%player%", player.getName())
                            .replace("%skill%", skill.toString())
                            .replace("%quantity%", String.valueOf(quantity))
                            .replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                    HyperSkills.getInstance().getApi().addXP(player.getUniqueId(), skill, quantity);
                } else{
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
        if(args.length == 3)
            return Arrays.stream(SkillType.values()).map(SkillType::getName).collect(Collectors.toList());
        return null;
    }

}
