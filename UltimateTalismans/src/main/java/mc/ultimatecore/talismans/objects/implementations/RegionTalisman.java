package mc.ultimatecore.talismans.objects.implementations;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RegionTalisman {
    List<String> getRegions();
    Set<UUID> getRegionPlayers();
}
