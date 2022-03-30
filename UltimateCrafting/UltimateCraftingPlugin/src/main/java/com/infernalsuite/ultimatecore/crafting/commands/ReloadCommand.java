package com.infernalsuite.ultimatecore.crafting.commands;

import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "hypercrafting.reload", true);
    }

    @Override
    public boolean execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        if (args.length == 1) {
            HyperCrafting.getInstance().reloadConfigs();
            p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
        }else{
            p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
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
