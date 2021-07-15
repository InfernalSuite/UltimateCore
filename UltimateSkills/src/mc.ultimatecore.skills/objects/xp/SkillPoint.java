package mc.ultimatecore.skills.objects.xp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.ultimatecore.skills.objects.SkillType;

@AllArgsConstructor
@Getter
public class SkillPoint {
    private final String id;
    private final SkillType skillType;
    private final Double xp;
}
