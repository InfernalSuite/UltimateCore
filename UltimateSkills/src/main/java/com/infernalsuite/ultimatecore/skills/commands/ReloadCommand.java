package com.infernalsuite.ultimatecore.skills.commands;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends HyperCommand {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hyperskills.reload", false, "/Skills reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperSkills.getInstance().reloadConfigs();
            cs.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
