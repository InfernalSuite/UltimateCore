package com.infernalsuite.ultimatecore.dragon.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;


public class GiveEnderEyeCommand extends Command {

    public GiveEnderEyeCommand() {
        super(Collections.singletonList("give"), "Give Ender Eye", "hyperdragon.give", false, "/HyperDragons give <player> <amount>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 3){
            Player player = Bukkit.getPlayer(args[1]);
            if(player != null) {
                try {
                    int amount = Integer.parseInt(args[2]);
                    NBTItem nbtItem = new NBTItem(InventoryUtils.makeItem(HyperDragons.getInstance().getConfiguration().item));
                    nbtItem.setBoolean("hyperdragon_key", true);
                    for(int i = 0; i<amount; i++)
                        player.getInventory().addItem(nbtItem.getItem());
                }catch (NumberFormatException e){
                    sender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidNumber").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
                }
            }else{
                sender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
            }
        }else{
            sender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
