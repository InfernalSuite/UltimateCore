package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.gui.ProfileGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ProfileGUICommand extends HyperCommand {

    public ProfileGUICommand() {
        super(Collections.singletonList("profile"), "Opens the profile menu", "hyperskills.profile", true, "/Skills profile");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(new ProfileGUI(p).getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
