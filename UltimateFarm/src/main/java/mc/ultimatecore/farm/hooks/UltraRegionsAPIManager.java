package mc.ultimatecore.farm.hooks;

import mc.ultimatecore.helper.regionwrapper.RegionPluginImpl;
import me.TechsCode.UltraRegions.UltraRegions;
import me.TechsCode.UltraRegions.selection.XYZ;
import me.TechsCode.UltraRegions.storage.ManagedWorld;
import me.TechsCode.UltraRegions.storage.Region;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UltraRegionsAPIManager implements RegionPluginImpl {
    
    @Override
    public boolean isInRegion(Location location, List<String> regions) {
        Optional<ManagedWorld> optional = UltraRegions.getInstance().getWorlds().find(location.getWorld());
        if (optional.isPresent()) {
            List<Region> ultraRegions = UltraRegions.getInstance().newRegionQuery(optional.get()).location(XYZ.from(location)).sortBySize().getRegions();
            if (regions.size() == 0)
                return false;
            for (Region region : ultraRegions)
                if (regions.contains(region.getName()))
                    return true;
        }
        return false;
    }
    
    @Override
    public boolean isInRegion(Location location, String region) {
        Optional<ManagedWorld> optional = UltraRegions.getInstance().getWorlds().find(location.getWorld());
        if (optional.isPresent()) {
            List<Region> ultraRegions = UltraRegions.getInstance().newRegionQuery(optional.get()).location(XYZ.from(location)).sortBySize().getRegions();
            if (region == null) return false;
            return ultraRegions.stream().anyMatch(ultraRegion -> ultraRegion.getName().equals(region));
        }
        return false;
    }
    
    @Override
    public Set<String> getRegions(Location location) {
        Optional<ManagedWorld> optional = UltraRegions.getInstance().getWorlds().find(location.getWorld());
        return optional.map(managedWorld -> UltraRegions.getInstance().newRegionQuery(managedWorld).location(XYZ.from(location)).sortBySize().getRegions().stream().map(Region::getName).collect(Collectors.toSet())).orElseGet(HashSet::new);
    }

   /* @Override
    public boolean matchRegion(Location location, String regionName) {
        if(location == null) return false;
        String regions = getRegionName(location);
        if(regions == null) return false;
        return regions.equalsIgnoreCase(regionName);
    }*/
}