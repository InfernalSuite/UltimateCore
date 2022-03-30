package com.infernalsuite.ultimatecore.souls.commands;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.utils.StringUtils;
import com.infernalsuite.ultimatecore.souls.objects.Soul;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SoulRemoveCommand extends Command {
    
    public SoulRemoveCommand() {
        super(Collections.singletonList("remove"), "Remove a placed soul", "hypersouls.remove", true, "/HyperSouls remove <id>");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2) {
            if (args[1] != null) {
                try {
                    int id = Integer.parseInt(args[1]);
                    Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(id);
                    if (!soul.isPresent()) return;
                    if (HyperSouls.getInstance().getSouls().removeSoul(soul.get()))
                        p.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("succesfullyRemoved").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix).replace("%soul_id%", String.valueOf(soul.get().getId()))));
                } catch (NumberFormatException e) {
                    p.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
                }
            }
        } else {
            p.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
    
}
