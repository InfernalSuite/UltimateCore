package mc.ultimatecore.pets.objects.potions;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Set;

@AllArgsConstructor
public class PetPotions {
    private final Set<PetPotion> potions;

    public void apply(Player player){
        potions.forEach(petPotion -> petPotion.addEffect(player));
    }

    public void remove(Player player){
        potions.forEach(petPotion -> petPotion.removeEffect(player));
    }
}
