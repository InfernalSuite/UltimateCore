package mc.ultimatecore.enchantment.object;

import lombok.*;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class EnchantObject {
    private final HyperEnchant hyperEnchant;
    @Setter
    private int level;

    public EnchantObject withLevel(int level) {
        return new EnchantObject(hyperEnchant, level);
    }
}
