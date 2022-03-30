package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.gui.TopGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TopCommand extends HyperCommand {

    public TopCommand() {
        super(Collections.singletonList("ranking"), "Opens the skills ranking menu", "hyperskills.ranking", true, "/Skills ranking");
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
