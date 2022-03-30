package com.infernalsuite.ultimatecore.pets.commands;

import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.playerdata.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MenuCommand extends Command {

    public MenuCommand() {
        super(Collections.singletonList("menu"), "Opens the pets menu", "pets.menu", true, "/Pets menu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = HyperPets.getInstance().getUserManager().getUser(p);
        p.openInventory(user.getPetGUI().getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }


}
