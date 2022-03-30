package mc.ultimatecore.pets.objects.potions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
@Getter
public class PetPotion {
    private final String potion;
    private final int level;

    public void addEffect(Player player){
        PotionEffectType effectType = PotionEffectType.getByName(potion);
        if(effectType == null) return;
        player.addPotionEffect(new PotionEffect(effectType, 99999999, level - 1));
    }

    public void removeEffect(Player player){
        PotionEffectType effectType = PotionEffectType.getByName(potion);
        if(effectType == null) return;
        player.removePotionEffect(effectType);
    }
}
