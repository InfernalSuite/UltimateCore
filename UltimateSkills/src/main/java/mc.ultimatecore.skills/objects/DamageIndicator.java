package mc.ultimatecore.skills.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.ultimatecore.skills.managers.IndicatorType;

@Getter
@AllArgsConstructor
public class DamageIndicator {
    private final IndicatorType indicatorType;
    private final boolean active;
    private final boolean degrade;
    private final String indicator;
}
