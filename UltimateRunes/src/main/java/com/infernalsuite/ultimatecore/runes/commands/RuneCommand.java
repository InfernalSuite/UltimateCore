package com.infernalsuite.ultimatecore.runes.commands;

import com.infernalsuite.ultimatecore.runes.HyperRunes;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RuneCommand extends Command {

    public RuneCommand() {
        super(Collections.singletonList("runes"), "Opens the runes menu", "hyperrunes.runesmenu", true, "/Hyperrunes runes");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(HyperRunes.getInstance().getRunesGUI().getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
