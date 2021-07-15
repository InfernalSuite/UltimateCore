package mc.ultimatecore.trades.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.trades.gui.TradesSetupGUI;
import mc.ultimatecore.trades.gui.TradingOptionsGUI;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TradeObject {

    private final String key;

    private final String displayName;

    private final List<String> description;

    private ItemStack tradeItem;

    private ItemStack costItem;

    private int slot;

    private int page;

    private double moneyCost;

    private String permission;

    private String category;

    private final TradingOptionsGUI tradingOptionsGUI;

    private final TradesSetupGUI shopAdminGUI;

    public TradeObject(String key, String displayName, List<String> description, ItemStack tradeMaterial, ItemStack costItem, int slot, int page, double moneyCost, String permission, String category) {
        this.key = key;
        this.displayName = displayName;
        this.description = description;
        this.tradeItem = tradeMaterial;
        this.costItem = costItem;
        this.slot = slot;
        this.moneyCost = moneyCost;
        this.permission = permission;
        this.page = page;
        this.category = category;
        tradingOptionsGUI = new TradingOptionsGUI(this);
        shopAdminGUI = new TradesSetupGUI(this);
    }

    public TradingOptionsGUI getTradingOptionsGUI() {
        return tradingOptionsGUI;
    }

    public TradesSetupGUI getShopAdminGUI() {
        return shopAdminGUI;
    }

}
