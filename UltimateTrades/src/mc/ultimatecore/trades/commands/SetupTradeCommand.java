package mc.ultimatecore.trades.commands;

import mc.ultimatecore.trades.HyperTrades;
import mc.ultimatecore.trades.objects.TradeObject;
import mc.ultimatecore.trades.utils.StringUtils;
import mc.ultimatecore.trades.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetupTradeCommand extends Command {

    public SetupTradeCommand() {
        super(Collections.singletonList("setuptrade"), "to edit or create new trades", "hypertrades.setuptrade", true, "/Hypertrades setuptrade [key]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String key = args[1];
            if(HyperTrades.getInstance().getTradesManager().getTradeByKey(key) == null)
                HyperTrades.getInstance().getTradesManager().tradeObjects.add(
                        new TradeObject(key, "&fTrade Item &8x%trade_item_amount%",
                                Arrays.asList("&f&lCOMMON", "", "&7Cost:", "&fCost Item &8x%cost_item_amount%"), null, null, 10, 1, 0D,
                                "hypertrades."+key, "main"
                        ));
            p.openInventory(HyperTrades.getInstance().getTradesManager().getTradeByKey(key).getShopAdminGUI().getInventory());
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
