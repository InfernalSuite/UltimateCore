package com.infernalsuite.ultimatecore.collections.commands;

import com.infernalsuite.ultimatecore.collections.gui.MainMenuGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MainMenuCommand extends Command {
    
    public MainMenuCommand() {
        super(Collections.singletonList("mainmenu"), "Opens the collections main menu", "hypercollections.mainmenu", true, "/Collections mainmenu");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(new MainMenuGUI(p.getUniqueId()).getInventory());
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
