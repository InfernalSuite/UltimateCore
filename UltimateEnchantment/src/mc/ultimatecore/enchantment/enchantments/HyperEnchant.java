package mc.ultimatecore.enchantment.enchantments;

import com.cryptomorin.xseries.XEnchantment;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.enchantments.hooks.HyperAdvancedEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public abstract class HyperEnchant {

    protected final String displayName;

    protected transient int maxLevel;

    protected final boolean useMoney;

    protected final Map<Integer, Double> requiredMoney;

    protected final String enchantmentName;

    protected final List<String> description;

    protected final Map<Integer, Integer> requiredLevel;

    protected final int requiredBookShelf;

    public int getRequiredLevel(int level) {
        return requiredLevel.getOrDefault(level, level * 5);
    }

    public Double getRequiredMoney(int level) {
        return requiredMoney.getOrDefault(level, level * 100d);
    }

    public abstract boolean itemCanBeEnchanted(ItemStack itemStack);

    public boolean hasEnchantmentByReforge(ItemStack itemStack){
        if(itemStack == null) return false;
        return new NBTItem(itemStack).hasKey("rf_"+getEnchantmentName());
    }

    public Optional<Enchantment> hasEnchantmentConflicts(ItemStack itemStack){
        return EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(itemStack).keySet()
                .stream()
                .filter(hyperEnchant -> !(hyperEnchant instanceof HyperAdvancedEnchantment))
                .map(HyperEnchant::getEnchantment)
                .filter(this::hasConflictWith).findFirst();
    }

    public abstract boolean itemIsEnchanted(ItemStack itemStack, int level);

    public ItemStack getEnchantedItem(ItemStack itemStack, int level, boolean reforged) {
        return EnchantmentsPlugin.getInstance().getEnchantsHandler().addEnchantment(itemStack, level, reforged, this);
    }

    public ItemStack removeEnchant(ItemStack itemStack) {
        return EnchantmentsPlugin.getInstance().getEnchantsHandler().removeEnchantment(itemStack, this);
    }

    public abstract int getItemLevel(ItemStack itemStack);

    public abstract boolean hasConflictWith(Enchantment enchantment);

    public Enchantment getEnchantment(){
        return XEnchantment.valueOf(enchantmentName.toUpperCase()).parseEnchantment();
    }

}
