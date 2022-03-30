package com.infernalsuite.ultimatecore.menu.commands;

import com.infernalsuite.ultimatecore.menu.gui.MainMenuGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MainMenuCommand extends Command {

    public MainMenuCommand() {
        super(Collections.singletonList("mainmenu"), "Opens main menu", "hypercore.mainmenu", true, "/Hypercore mainmenu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(new MainMenuGUI(p.getUniqueId()).getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
