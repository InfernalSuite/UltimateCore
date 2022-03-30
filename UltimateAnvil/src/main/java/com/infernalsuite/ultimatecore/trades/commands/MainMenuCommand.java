package com.infernalsuite.ultimatecore.trades.commands;

import com.infernalsuite.ultimatecore.trades.HyperTrades;
import com.infernalsuite.ultimatecore.trades.gui.TradesGUI;
import com.infernalsuite.ultimatecore.trades.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenuCommand extends Command {

    public MainMenuCommand() {
        super(Collections.singletonList("mainmenu"), "Opens the trades menu", "hypertrades.mainmenu", true, "/Hypertrades mainmenu [category]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String category = args[1];
            if(HyperTrades.getInstance().getTradesManager().getTradeCategories().contains(category))
                p.openInventory(new TradesGUI(p.getUniqueId(), 1, category).getInventory());
            else
                p.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("invalidCategory").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
        }else{
            p.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        if(args.length == 2)
            return new ArrayList<>(HyperTrades.getInstance().getTradesManager().getTradeCategories());
        return null;
    }

}
