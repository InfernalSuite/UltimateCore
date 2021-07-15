package mc.ultimatecore.anvil.objects;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
@Setter
public class EnchantItem {
    private final String name;
    private ItemStack itemStack;
    private int cost;

    public ItemStack getItemStack(){
        return itemStack.clone();
    }

    public ItemStack getItem(){
        NBTItem nbtItem = new NBTItem(itemStack.clone());
        nbtItem.setInteger("applyCost", cost);
        return nbtItem.getItem();
    }
}
