package mc.ultimatecore.runes.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.ultimatecore.runes.enums.RuneEffect;
import mc.ultimatecore.runes.enums.RuneType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Rune {
    private final String runeName;
    private final String displayName;
    private final String texture;
    private final RuneEffect effect;
    private final List<String> lore;
    private final RuneType runeType;
    private final Map<Integer, Integer> requiredRunecraftingLevel = new HashMap<Integer, Integer>() {{
        put(1, 2);
        put(2, 4);
        put(3, 6);
        put(4, 8);
    }};
    private final Map<Integer, Integer> successChance = new HashMap<Integer, Integer>() {{
        put(1, 70);
        put(2, 50);
        put(3, 25);
        put(4, 10);
    }};

    public Rune(String runeName, String displayName, List<String> lore , String texture, RuneEffect effect, RuneType runeType) {
        this.runeName = runeName;
        this.displayName = displayName;
        this.texture = texture;
        this.lore = lore;
        this.runeType = runeType;
        this.effect = effect;
    }

    public int getRequiredLevel(int level) {
        return requiredRunecraftingLevel.get(level);
    }

    public int getSuccessChance(int level) {
        return successChance.get(level);
    }
}
