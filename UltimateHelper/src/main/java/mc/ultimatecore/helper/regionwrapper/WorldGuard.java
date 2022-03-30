package mc.ultimatecore.helper.regionwrapper;

import org.bukkit.Location;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WorldGuard implements RegionPluginImpl {
    
    @Override
    public boolean isInRegion(Location location, List<String> regions) {
        Set<String> locationRegions = getRegions(location);
        for(String region : regions)
            return locationRegions.contains(region);
        return false;
    }
    
    @Override
    public boolean isInRegion(Location location, String region) {
        return getRegions(location).contains(region);
    }
    
    @Override
    public Set<String> getRegions(Location location) {
        Set<IWrappedRegion> regions = WorldGuardWrapper.getInstance().getRegions(location);
        return regions.stream().map(IWrappedRegion::getId).collect(Collectors.toSet());
    }
    
}