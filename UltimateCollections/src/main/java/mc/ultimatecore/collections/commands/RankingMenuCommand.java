package mc.ultimatecore.collections.commands;

import mc.ultimatecore.collections.gui.TopGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RankingMenuCommand extends Command {
    
    public RankingMenuCommand() {
        super(Collections.singletonList("ranking"), "Opens the ranking menu", "hypercollections.ranking", true, "/Collections ranking");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(new TopGUI(p.getUniqueId()).getInventory());
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
