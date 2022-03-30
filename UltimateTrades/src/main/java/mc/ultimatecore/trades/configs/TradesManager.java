package mc.ultimatecore.trades.configs;


import mc.ultimatecore.trades.HyperTrades;
import mc.ultimatecore.trades.enums.EditType;
import mc.ultimatecore.trades.objects.TradeObject;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class TradesManager extends YAMLFile {
    private final Map<UUID, HashMap<String, EditType>> setupPlayers = new HashMap<>();

    public List<TradeObject> tradeObjects = new ArrayList<>();

    public TradesManager(HyperTrades hyperTrades, String name) {
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
        tradeObjects = new ArrayList<>();
        try {
            ConfigurationSection section = getConfig().get().getConfigurationSection("trades");
            if (section != null)
                for (String tradeKey : section.getKeys(false)) {
                    String tradeItemDisplayName = getConfig().get().getString("trades." + tradeKey + ".tradeItemDisplayName");
                    List<String> description = getConfig().get().getStringList("trades." + tradeKey + ".description");
                    double requiredMoney = getConfig().get().getDouble("trades." + tradeKey + ".moneyCost");
                    int page = getConfig().get().getInt("trades." + tradeKey + ".page");
                    ItemStack tradeItem = getConfig().get().getItemStack("trades." + tradeKey + ".tradeItem");
                    ItemStack costItem = getConfig().get().getItemStack("trades." + tradeKey + ".costItem");
                    int slot = getConfig().get().getInt("trades." + tradeKey + ".slot");
                    String permission = getConfig().get().getString("trades." + tradeKey + ".permission");
                    String category = getConfig().get().getString("trades." + tradeKey + ".category");
                    if(category == null)
                        category = "main";
                    TradeObject tradeObject = new TradeObject(tradeKey, tradeItemDisplayName, description, tradeItem, costItem, slot, page, requiredMoney, permission, category);
                    this.tradeObjects.add(tradeObject);
                }
        } catch (Exception ignored) {
        }
    }

    public void save() {
        try {
            for (TradeObject tradeObject : this.tradeObjects) {
                getConfig().set("trades." + tradeObject.getKey() + ".tradeItemDisplayName", tradeObject.getDisplayName());
                getConfig().set("trades." + tradeObject.getKey() + ".description", tradeObject.getDescription());
                getConfig().set("trades." + tradeObject.getKey() + ".moneyCost", tradeObject.getMoneyCost());
                getConfig().set("trades." + tradeObject.getKey() + ".tradeItem", tradeObject.getTradeItem());
                getConfig().set("trades." + tradeObject.getKey() + ".costItem", tradeObject.getCostItem());
                getConfig().set("trades." + tradeObject.getKey() + ".slot", tradeObject.getSlot());
                getConfig().set("trades." + tradeObject.getKey() + ".page", tradeObject.getPage());
                getConfig().set("trades." + tradeObject.getKey() + ".permission", tradeObject.getPermission());
            }
            getConfig().save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TradeObject getTradeByKey(String key) {
        for (TradeObject tradeObject : this.tradeObjects) {
            if (tradeObject.getKey().equals(key))
                return tradeObject;
        }
        return null;
    }

    public Set<String> getTradeCategories(){
        return tradeObjects.stream().map(TradeObject::getCategory).collect(Collectors.toSet());
    }

    public Set<String> getTradeKeys(){
        return tradeObjects.stream().map(TradeObject::getKey).collect(Collectors.toSet());
    }

    public HashMap<String, EditType> getSetupMode(UUID uuid){
        if(!setupPlayers.containsKey(uuid) || setupPlayers.get(uuid) == null) return null;
        return setupPlayers.get(uuid);
    }

    public void setSetupMode(UUID uuid, String key, EditType editType){
        setupPlayers.put(uuid, new HashMap<String, EditType>(){{
            put(key, editType);
        }});
    }

    public void removeSetupMode(UUID uuid){
        setupPlayers.remove(uuid);
    }
}
