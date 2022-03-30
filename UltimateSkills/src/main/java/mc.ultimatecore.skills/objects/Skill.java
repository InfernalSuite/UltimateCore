package mc.ultimatecore.skills.objects;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Skill {
    private final String name;
    private final List<String> description;
    private final List<String> blockedWorlds;
    private final List<String> blockedRegions;
    private final boolean isEnabled;
    private final boolean multiplierEnabled;
    private final boolean xpInCreative;
    private final int slot;
    private final XMaterial xMaterial;
    private transient final SkillType skillType;
}
