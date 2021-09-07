package mc.ultimatecore.crafting.configs;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;


public class BlackList extends YAMLFile {
    @Getter
    private Set<String> blockedCrafts;

    public BlackList(HyperCrafting hyperCrafting, String name, boolean loadDefaults) {
        super(hyperCrafting, name, loadDefaults);
        loadDefaults();
    }

    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        blockedCrafts = new HashSet<>();
        try {
            blockedCrafts = new HashSet<>(getConfig().getStringList("disabledcrafts"));
        } catch (Exception ignored) {
        }
    }

    public boolean itemIsBlackListed(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return false;
        String key = itemStack.getType().toString();
        if (XMaterial.getVersion() > 13) {
            return blockedCrafts
                    .contains(key);
        } else {
            for (String mat : blockedCrafts) {
                try {
                    XMaterial material = XMaterial.valueOf(mat);
                    String legacy = material.getLegacy().length > 0 ? material.getLegacy()[0] : material.toString();
                    if (key.equals(legacy) && itemStack.getData().getData() == material.getData())
                        return true;
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(Utils.color("&e[UltimateCrafting] &cError unknown blacklisted item " + mat + "!"));
                }
            }
            return false;
        }
    }
}
