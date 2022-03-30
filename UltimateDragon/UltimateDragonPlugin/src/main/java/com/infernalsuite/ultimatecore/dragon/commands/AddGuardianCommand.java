package com.infernalsuite.ultimatecore.dragon.commands;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IGuardian;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;


public class AddGuardianCommand extends Command {

    public AddGuardianCommand() {
        super(Collections.singletonList("addguardian"), "Add New Dragon's guardian", "hyperdragon.addguardian", true, "/HyperDragons addguardian <name>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            IGuardian guardian = HyperDragons.getInstance().getDragonGuardians().getGuardian(args[1]);
            if(guardian == null){
                HyperDragons.getInstance().getDragonGuardians().addGuardian(args[1]);
                p.sendMessage(StringUtils.color(HyperDragons.getInstance().getConfiguration().prefix+" &aThe Guardian "+args[1]+" Succesfully added!"));
            }else{
                p.sendMessage(StringUtils.color(HyperDragons.getInstance().getConfiguration().prefix+" &aThis guardian already exist!"));
            }
        }else{
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
