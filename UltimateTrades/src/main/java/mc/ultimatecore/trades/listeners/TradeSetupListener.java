package mc.ultimatecore.trades.listeners;

import mc.ultimatecore.trades.HyperTrades;
import mc.ultimatecore.trades.enums.EditType;
import mc.ultimatecore.trades.objects.TradeObject;
import mc.ultimatecore.trades.utils.StringUtils;
import mc.ultimatecore.trades.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class TradeSetupListener implements Listener {

    @EventHandler
    public void checkChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        HashMap<String, EditType> setupMode = HyperTrades.getInstance().getTradesManager().getSetupMode(uuid);
        if (setupMode != null && !setupMode.isEmpty()) {
            Optional<String> key = setupMode.keySet().stream().findFirst();
            EditType edit = setupMode.get(key.get());
            event.setCancelled(true);
            String message = event.getMessage();
            TradeObject tradeObject = HyperTrades.getInstance().getTradesManager().getTradeByKey(key.get());
            if(message.contains("leave") || message.contains("exit") || message.contains("stop")){
                Utils.openGUIAsync(player, tradeObject);
            }else{
                try{
                    switch (edit){
                        case MONEY_COST:
                            double price = Double.parseDouble(message);
                            tradeObject.setMoneyCost(price);
                            break;
                        case PERMISSION:
                            tradeObject.setPermission(message);
                            break;
                        case TRADE_PAGE:
                            int page = Integer.parseInt(message);
                            tradeObject.setPage(page);
                            break;
                        case TRADE_SLOT:
                            int slot = Integer.parseInt(message);
                            tradeObject.setSlot(slot);
                            break;
                        case CATEGORY:
                            tradeObject.setCategory(message);
                            break;
                    }
                    Utils.openGUIAsync(player, tradeObject);
                } catch (NumberFormatException e) {
                    player.sendMessage(StringUtils.color(HyperTrades.getInstance().getMessages().getMessage("invalidNumber").replace("%prefix%", HyperTrades.getInstance().getConfiguration().prefix)));
                }
                HyperTrades.getInstance().getTradesManager().removeSetupMode(uuid);
            }
            HyperTrades.getInstance().getTradesManager().removeSetupMode(uuid);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        HyperTrades.getInstance().getTradesManager().removeSetupMode(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeave(PlayerKickEvent event){
        HyperTrades.getInstance().getTradesManager().removeSetupMode(event.getPlayer().getUniqueId());
    }
}
