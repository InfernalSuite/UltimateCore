package com.infernalsuite.ultimatecore.pets.commands;

import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "pets.reload", false, "/Pets reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            HyperPets.getInstance().reloadConfigs();
            cs.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
