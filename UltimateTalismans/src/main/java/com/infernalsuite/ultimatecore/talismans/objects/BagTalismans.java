package com.infernalsuite.ultimatecore.talismans.objects;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.utils.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
