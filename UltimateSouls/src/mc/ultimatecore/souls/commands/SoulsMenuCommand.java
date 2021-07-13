package mc.ultimatecore.souls.commands;

import mc.ultimatecore.souls.HyperSouls;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SoulsMenuCommand extends Command {
    
    public SoulsMenuCommand() {
        super(Collections.singletonList("menu"), "Opens the souls menu", "hypersouls.mainmenu", true, "/HyperSouls menu");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(HyperSouls.getInstance().getAllSoulsGUI().get(1).getInventory());
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
