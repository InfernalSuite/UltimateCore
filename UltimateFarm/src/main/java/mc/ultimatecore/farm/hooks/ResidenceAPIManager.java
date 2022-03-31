package mc.ultimatecore.farm.hooks;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import mc.ultimatecore.helper.regionwrapper.RegionPluginImpl;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResidenceAPIManager implements RegionPluginImpl {
    
    @Override
    public boolean isInRegion(Location location, List<String> regions) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        return res != null && regions.contains(res.getName());
    }
    
    @Override
    public boolean isInRegion(Location location, String region) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        return res != null && region.contains(res.getName());
    }
    
    @Override
    public Set<String> getRegions(Location location) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        return res != null ? new HashSet<>(Collections.singletonList(res.getName())) : new HashSet<>();
    }
}
