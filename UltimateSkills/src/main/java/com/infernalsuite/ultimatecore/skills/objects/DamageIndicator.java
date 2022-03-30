package com.infernalsuite.ultimatecore.skills.objects;

import com.infernalsuite.ultimatecore.skills.managers.IndicatorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DamageIndicator {
    private final IndicatorType indicatorType;
    private final boolean active;
    private final boolean degrade;
    private final String indicator;
}
