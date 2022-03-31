package mc.ultimatecore.reforge.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;

@AllArgsConstructor
@Getter
public class EnchantmentProbability {
    private final String id;
    private final HyperEnchant hyperEnchant;
    private final int level;
    private final int chance;
}
