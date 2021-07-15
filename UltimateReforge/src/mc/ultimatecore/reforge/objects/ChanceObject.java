package mc.ultimatecore.reforge.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.ultimatecore.reforge.enums.ItemType;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChanceObject {
    private final ItemType itemType;
    private final double costPerEnchant;
    private final int newEnchantsPerEnchant;
    private final List<EnchantmentProbability> enchantmentProbabilities;
}
