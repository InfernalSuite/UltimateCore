package com.infernalsuite.ultimatecore.alchemy.commands;

import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hyperalchemy.reload", true);
    }

    @Override
    public boolean execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        if (args.length == 1) {
            HyperAlchemy.getInstance().reloadConfigs();
            p.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
        }else{
            p.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
        }
        return false;
    }

    @Override
    public void admin(CommandSender sender, String[] args) {
        execute(sender, args);
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
