package mc.ultimatecore.anvil.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class NextCost {
    private HashMap<HyperEnchant, Integer> hyperEnchant;
    private int level;

    public void setValues(HyperEnchant hyperEnchant, int level){
        this.level = level;
    }
}
