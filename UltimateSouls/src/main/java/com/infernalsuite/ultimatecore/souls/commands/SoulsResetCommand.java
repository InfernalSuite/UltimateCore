package com.infernalsuite.ultimatecore.souls.commands;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.utils.StringUtils;
import com.infernalsuite.ultimatecore.souls.objects.PlayerSouls;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SoulsResetCommand extends Command {
    
    public SoulsResetCommand() {
        super(Collections.singletonList("reset"), "Reset all souls from a player", "hypersouls.reset", true, "/HyperSouls reset <player>");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                PlayerSouls playerSouls = HyperSouls.getInstance().getDatabaseManager().getSoulsData(player);
                if (playerSouls == null) return;
                playerSouls.resetData();
                if (sender instanceof Player)
                    sender.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("resetData").replace("%player%", player.getName()).replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
            } else {
                sender.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
            }
        } else {
            sender.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
}
