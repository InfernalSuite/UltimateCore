package mc.ultimatecore.pets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tier {
    private final String name;
    private final String displayName;
    private final int tierValue;
}
