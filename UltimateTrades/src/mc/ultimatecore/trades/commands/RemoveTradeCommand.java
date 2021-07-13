package mc.ultimatecore.trades.commands;

import mc.ultimatecore.trades.HyperTrades;
import mc.ultimatecore.trades.objects.TradeObject;
import mc.ultimatecore.trades.utils.StringUtils;
import mc.ultimatecore.trades.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoveTradeCommand extends Command {

    public RemoveTradeCommand() {
        super(Collections.singletonList("removetrade"), "to remove a trade", "hypertrades.removetrade", true, "/Hypetrades removetrade [key]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String key = args[1];
            TradeObject tradeObject = HyperTrades.getInstance().getTradesManager().getTradeByKey(key);
            if(tradeObject == null){
                p.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("invalidTrade").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
            }else{
                HyperTrades.getInstance().getTradesManager().tradeObjects.remove(tradeObject);
                p.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("successfullyRemoved").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
            }
        }else{
            p.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        if(args.length == 2)
            return new ArrayList<>(HyperTrades.getInstance().getTradesManager().getTradeKeys());
        return null;
    }

}
