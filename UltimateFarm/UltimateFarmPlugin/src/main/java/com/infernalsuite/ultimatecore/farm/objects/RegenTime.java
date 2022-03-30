package com.infernalsuite.ultimatecore.farm.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.infernalsuite.ultimatecore.farm.utils.Utils;

@AllArgsConstructor
@Getter
public class RegenTime {
    private final int max;
    private final int min;

    public int getRegenTime() {
        if (max == min || min > max) return max;
        return Utils.getRandom(min, max);
    }
}
