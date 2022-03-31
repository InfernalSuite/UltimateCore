package mc.ultimatecore.dragon.objects.rewards;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DragonReward {
    private final int damageDone;
    private final List<String> commands;
}
