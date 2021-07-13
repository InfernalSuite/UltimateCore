package mc.ultimatecore.souls.commands;

import mc.ultimatecore.souls.HyperSouls;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SoulsGetCommand extends Command {
    
    public SoulsGetCommand() {
        super(Collections.singletonList("get"), "Get a soul", "hypersouls.mainmenu", true, "/HyperSouls get");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.getInventory().addItem(HyperSouls.getInstance().getSouls().getSoulItem());
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
}
