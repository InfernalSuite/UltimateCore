package com.infernalsuite.ultimatecore.dragon.commands;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.guardian.DragonGuardian;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IGuardian;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class SpawnGuardianCommand extends Command {

    public SpawnGuardianCommand() {
        super(Collections.singletonList("spawnguardian"), "SpawnGuardian", "hyperdragon.spawnguardian", true, "/HyperDragons spawnguardian <name>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            IGuardian guardian = HyperDragons.getInstance().getDragonGuardians().getGuardian(args[1]);
            if(guardian != null)
                guardian.spawn(p.getLocation());
            else
                p.sendMessage(StringUtils.color(HyperDragons.getInstance().getConfiguration().prefix+" &aThis guardian doesn't exists!"));
        }else{
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length == 1)
            return HyperDragons.getInstance().getDragonGuardians().getGuardianMap().values().stream().map(DragonGuardian::getID).collect(Collectors.toList());
        return null;
    }

}
