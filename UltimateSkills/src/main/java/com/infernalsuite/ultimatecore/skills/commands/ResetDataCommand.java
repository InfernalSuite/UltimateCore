package com.infernalsuite.ultimatecore.skills.commands;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ResetDataCommand extends HyperCommand {

    public ResetDataCommand() {
        super(Collections.singletonList("resetdata"), "Reset all player data", "hyperskills.resetdata", true, "/Skills resetdata [player]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            try {
                Player executor = (Player) sender;
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    resetDataExecute(executor, player);
                }else {
                    sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }
            } catch (IllegalArgumentException e) {
                sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
            }

        } else {
            sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

    private void resetDataExecute(Player sender, Player receiver){
        UUID receiverUUID = receiver.getUniqueId();
        if(HyperSkills.getInstance().getConfiguration().resetDataConfirm && HyperSkills.getInstance().getResetDataManager().addPlayer(receiverUUID, sender.getUniqueId())){
            sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("confirmation").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }else{
            HyperSkills.getInstance().getResetDataManager().resetData(receiverUUID);
            if(!HyperSkills.getInstance().getConfiguration().resetDataMessage) return;
            sender.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("dataResetSender")
                    .replace("%player%", receiver.getName())
                    .replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
            if(sender != receiver)
                receiver.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("dataResetReceiver")
                    .replace("%player%", sender.getName())
                    .replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

}