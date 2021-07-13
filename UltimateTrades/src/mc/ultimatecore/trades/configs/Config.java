package mc.ultimatecore.trades.configs;

import mc.ultimatecore.trades.HyperTrades;

public class Config extends YAMLFile{
    public String prefix;
    public String successTradeSound;
    public String failTradeSound;
    public String mainCommandPerm;

    public Config(HyperTrades hyperTrades, String name) {
        super(hyperTrades, name);
    }

    @Override
    public void enable(){
        super.enable();
        this.loadDefaults();
    }

    @Override
    public void reload() {
        getConfig().reload();
        this.loadDefaults();
    }

    private void loadDefaults() {
        prefix = getConfig().get().getString("prefix", "&e&lHyper Trades &7");
        successTradeSound = getConfig().get().getString("successTradeSound", "ENTITY_EXPERIENCE_ORB_PICKUP");
        failTradeSound = getConfig().get().getString("failTradeSound", "ENTITY_VILLAGER_NO");
        mainCommandPerm = getConfig().get().getString("mainCommandPerm", "");
    }
}
