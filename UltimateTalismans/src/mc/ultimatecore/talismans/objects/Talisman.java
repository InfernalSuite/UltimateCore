package mc.ultimatecore.talismans.objects;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.talismans.Item;
import mc.ultimatecore.talismans.objects.implementations.TalismanImpl;
import mc.ultimatecore.talismans.utils.InventoryUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public abstract class Talisman extends TalismanImpl {
    private String name;
    private TalismanType talismanType;
    private String displayName;
    private List<String> lore;
    private String texture;
    private boolean executable;

    public ItemStack getItem(){
        ItemStack itemStack = InventoryUtils.makeItem(new Item(XMaterial.PLAYER_HEAD, 0, texture, 1, displayName, lore));
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("uc_talisman", true);
        nbtItem.setBoolean("uc_executable", executable);
        nbtItem.setString("uc_talisman_name", name);
        nbtItem.setString("uc_talisman_type", talismanType.name());
        nbtItem.setString("uc_talisman_uuid", UUID.randomUUID().toString());
        return nbtItem.getItem();
    }
}
