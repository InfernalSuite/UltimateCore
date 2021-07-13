package mc.ultimatecore.souls.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.Item;
import mc.ultimatecore.souls.objects.Soul;
import mc.ultimatecore.souls.objects.SoulParticle;
import mc.ultimatecore.souls.utils.InventoryUtils;
import mc.ultimatecore.souls.utils.StringUtils;
import mc.ultimatecore.souls.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Souls extends YAMLFile {
    
    public Map<Location, Soul> souls = new HashMap<>();
    
    public Souls(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }
    
    
    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }
    
    public void save() {
        ConfigurationSection configurationSection = getConfig().getConfigurationSection("guardians");
        if (configurationSection != null) {
            for (String str : configurationSection.getKeys(false))
                getConfig().set("souls." + str, null);
        }
        super.save();
        for (Soul soul : souls.values()) {
            getConfig().set("souls." + soul.getId() + ".particle", soul.getParticle().particleName());
            getConfig().set("souls." + soul.getId() + ".moneyReward", soul.getMoneyReward());
            getConfig().set("souls." + soul.getId() + ".commandRewards", soul.getCommandRewards());
            Utils.saveLoc(getConfig(), "souls." + soul.getId() + ".location", soul.getLocation());
        }
        super.save();
    }
    
    private void loadDefaults() {
        souls = new HashMap<>();
        YamlConfiguration cf = getConfig();
        ConfigurationSection section = cf.getConfigurationSection("souls");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                try {
                    int id = Integer.parseInt(key);
                    Location location = Utils.loadLocation(getConfig(), "souls." + key + ".location");
                    List<String> commandRewards = cf.getStringList("souls." + key + ".commandRewards");
                    double moneyReward = cf.getDouble("souls." + key + ".moneyReward");
                    SoulParticle particle = cf.contains("souls." + key + ".particle") ? SoulParticle.valueOf(cf.getString("souls." + key + ".particle")) : null;
                    souls.put(location, new Soul(id, location, commandRewards, moneyReward, particle));
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.getLogger().warning("Error Loading Soul " + key);
                }
            }
        }
        if (souls.size() > 0)
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperSouls] &aSuccessfully loaded " + souls.size() + " souls!"));
    }
    
    public Optional<Soul> getSoulByID(int id) {
        return souls.values().stream().filter(soul -> soul.getId() == id).findFirst();
    }
    
    public Soul getSoulByLocation(Location location) {
        return souls.getOrDefault(location, null);
    }
    
    public ItemStack getSoulItem() {
        try {
            Item item = new Item(XMaterial.PLAYER_HEAD, 1, HyperSouls.getInstance().getConfiguration().soulTexture, 1, StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("soulToPlaceName")), new ArrayList<>());
            return InventoryUtils.makeItem(item);
        } catch (Exception ignored) {
        }
        return XMaterial.STONE.parseItem();
    }
    
    public int getNextID() {
        List<Integer> soulsId = souls.values().stream().map(Soul::getId).collect(Collectors.toList());
        return getMissingNumber(soulsId);
    }
    
    private int getMissingNumber(List<Integer> a) {
        int toReturn = 0;
        for (int i = 1; i <= a.size(); i++, toReturn++)
            if (!a.contains(i))
                return i;
        return toReturn + 1;
    }
    
    public boolean removeSoul(Soul soul) {
        if (soul == null) return false;
        Location location = soul.getLocation();
        if (!souls.containsKey(location)) return false;
        souls.remove(location);
        location.getBlock().setType(XMaterial.AIR.parseMaterial());
        HyperSouls.getInstance().getAllSoulsGUI().get(1).getInventory().clear();
        return true;
    }
    
}
