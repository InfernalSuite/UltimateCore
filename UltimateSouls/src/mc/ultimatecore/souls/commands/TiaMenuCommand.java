package mc.ultimatecore.souls.commands;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.gui.TiaGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TiaMenuCommand extends Command {
    
    public TiaMenuCommand() {
        super(Collections.singletonList("tiamenu"), "Opens the tia menu", "hypersouls.tiamenu", true, "/HyperSouls tiamenu");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(new TiaGUI(HyperSouls.getInstance(), p.getUniqueId()).getInventory());
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
