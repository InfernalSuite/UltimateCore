package mc.ultimatecore.talismans.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BagTalismans {
    @Getter
    private final UUID uuid;
    @Getter
    private List<String> talismans = new ArrayList<>();

    public void setTalismans(List<String> talismans){
        this.talismans = talismans;
    }

    public void setTalismans(String talismans){
        if(talismans.equals("")) return;
        String[] temp = Utils.getSplitList(talismans);
        if(temp == null) return;
        this.talismans.clear();
        Arrays.stream(Utils.getSplitList(talismans)).collect(Collectors.toList()).forEach(name -> {
            Talisman talisman = HyperTalismans.getInstance().getTalismans().getTalisman(name);
            if(talisman != null)
                this.talismans.add(name);
        });
    }

    public boolean hasTalisman(String name){
        return talismans.contains(name);
    }
}
