package mc.ultimatecore.skills.configs;

import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class UltimateItems extends YAMLFile {

    public Map<String, UltimateItem> ultimateItems = new HashMap<>();

    private final Map<UUID, Map<String, String>> setupPlayers = new HashMap<>();

    public UltimateItems(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        ultimateItems.clear();
        try{
            ConfigurationSection configurationSection = getConfig().getConfigurationSection("epicItems");
            if(configurationSection != null){
                for(String id : configurationSection.getKeys(false)) {
                    UltimateItem ultimateItem = new UltimateItem(id, new HashMap<>(), new HashMap<>());
                    if(getConfig().contains("epicItems."+id+".item"))
                        ultimateItem.setItemStack(getConfig().getItemStack("epicItems."+id+".item"));
                    if(getConfig().contains("epicItems."+id+".manaCost"))
                        ultimateItem.setManaCost(getConfig().getDouble("epicItems."+id+".manaCost"));
                    if(getConfig().contains("epicItems."+id+".attributes")) {
                        List<String> attributes = getConfig().getStringList("epicItems."+id+".attributes");
                        for(String key : attributes){
                            String[] attributeSplit = key.split(":");
                            if(attributeSplit.length != 2) continue;
                            String attributeName = attributeSplit[0];
                            Double attributeQuantity = Double.valueOf(attributeSplit[1]);
                            for(Ability ability : Ability.values())
                                if(ability.toString().equalsIgnoreCase(attributeName))
                                    ultimateItem.setAbility(ability, attributeQuantity);
                            for(Perk perk : Perk.values())
                                if(perk.toString().equalsIgnoreCase(attributeName))
                                    ultimateItem.setPerk(perk, attributeQuantity);
                        }
                    }
                    if(getConfig().contains("epicItems."+id+".inHand"))
                        ultimateItem.setEffectInHand(getConfig().getBoolean("epicItems."+id+".inHand"));
                    ultimateItems.put(id, ultimateItem);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            getConfig().set("epicItems", null);
            for (UltimateItem ultimateItem : ultimateItems.values()) {
                getConfig().set("epicItems." + ultimateItem.getId() + ".item", ultimateItem.getItemStack());
                getConfig().set("epicItems." + ultimateItem.getId() + ".manaCost", ultimateItem.getManaCost());
                getConfig().set("epicItems." + ultimateItem.getId() + ".inHand", ultimateItem.isEffectInHand());
                List<String> abilitiesMap = new ArrayList<>();
                for(Ability ability : Ability.values())
                    abilitiesMap.add(ability.toString()+":"+ultimateItem.getAbility(ability));
                getConfig().set("epicItems." + ultimateItem.getId() + ".attributes", abilitiesMap);
            }
            super.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getSetupMode(UUID uuid){
        if(!setupPlayers.containsKey(uuid) || setupPlayers.get(uuid) == null) return null;
        return setupPlayers.get(uuid);
    }

    public void setSetupMode(UUID uuid, String key, String editType){
        setupPlayers.put(uuid, new HashMap<String, String>(){{
            put(key, editType);
        }});
    }

    public void removeSetupMode(UUID uuid){
        setupPlayers.remove(uuid);
    }

    public void removeItem(String id){
        ultimateItems.remove(id);
    }


}
