package com.infernalsuite.ultimatecore.dragon.commands;

import com.infernalsuite.ultimatecore.dragon.inventories.DragonGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;


public class MenuCommand extends Command {

    public MenuCommand() {
        super(Collections.singletonList("menu"), "Open Dragon Settings Menu", "hyperdragon.menu", true, "/HyperDragon menu");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.openInventory(new DragonGUI().getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }

}
