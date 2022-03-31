package mc.ultimatecore.dragon.objects.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DragonStructure {
    private final Set<DragonAltar> altars = new HashSet<>();
    private final Set<Location> crystals = new HashSet<>();
    private Location spawnLocation;
    private boolean inUse;

    public boolean removeAltar(Location location){
        Optional<DragonAltar> optional = getAltar(location);
        if(!optional.isPresent()) return false;
        altars.remove(optional.get());
        return true;
    }

    public Optional<DragonAltar> getAltar(Location location){
        return altars.stream().filter(altar -> altar.getLocation().equals(location)).findFirst();
    }

    public boolean addAltar(DragonAltar dragonAltar){
        if(getAltar(dragonAltar.getLocation()).isPresent()) return false;
        return altars.add(dragonAltar);
    }
}
