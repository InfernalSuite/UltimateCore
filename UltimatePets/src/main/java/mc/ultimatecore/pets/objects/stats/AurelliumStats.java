package mc.ultimatecore.pets.objects.stats;

import com.archyx.aureliumskills.stats.Stat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.ultimatecore.pets.HyperPets;
import org.bukkit.entity.Player;

import java.util.Map;

@AllArgsConstructor
@Getter
public class AurelliumStats implements PetStats{
    private final Map<Stat, Double> petAbilities;

    @Override
    public void addStats(Player player){
        petAbilities.forEach((ability, amount) -> HyperPets.getInstance().getAddonsManager().getAurelliumSkills()
                .addStats(player, ability, amount));
    }

    @Override
    public void removeStats(Player player){
        petAbilities.forEach((ability, amount) -> HyperPets.getInstance().getAddonsManager().getAurelliumSkills()
                .removeStats(player, ability));
    }
}