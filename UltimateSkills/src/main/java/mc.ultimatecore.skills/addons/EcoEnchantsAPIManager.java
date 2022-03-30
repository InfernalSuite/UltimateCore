package mc.ultimatecore.skills.addons;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import mc.ultimatecore.skills.implementations.SoftDependImpl;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class EcoEnchantsAPIManager extends SoftDependImpl {
    public EcoEnchantsAPIManager(String displayName) {
        super(displayName);
    }

    public boolean hasEnchantment(ItemStack itemStack, String enchantment){
        return itemStack.getEnchantmentLevel(instance(enchantment)) > 0;
    }

    public EcoEnchant instance(String name){
        return EcoEnchants.getByKey(new NamespacedKey("minecraft", name));
    }
}
