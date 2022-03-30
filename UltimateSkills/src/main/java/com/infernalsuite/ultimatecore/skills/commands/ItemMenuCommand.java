package com.infernalsuite.ultimatecore.skills.commands;

import com.infernalsuite.ultimatecore.skills.gui.AllArmorsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ItemMenuCommand extends HyperCommand {

    public ItemMenuCommand() {
        super(Collections.singletonList("itemmenu"), "Opens the item main menu", "hyperskills.itemmenu", true, "/Skills itemmenu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(new AllArmorsGUI(1).getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
