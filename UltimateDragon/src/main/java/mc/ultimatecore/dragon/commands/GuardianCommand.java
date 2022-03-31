package mc.ultimatecore.dragon.commands;

import mc.ultimatecore.dragon.inventories.AllGuardiansGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;


public class GuardianCommand extends Command {

    public GuardianCommand() {
        super(Collections.singletonList("guardians"), "Open Guardians Menu", "hyperdragon.guardians", true, "/HyperDragon guardians");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.openInventory(new AllGuardiansGUI(1).getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }

}
