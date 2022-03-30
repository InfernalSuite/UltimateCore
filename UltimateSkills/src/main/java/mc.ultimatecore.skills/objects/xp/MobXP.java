package mc.ultimatecore.skills.objects.xp;

import lombok.Getter;
import mc.ultimatecore.skills.objects.SkillType;

@Getter
public class MobXP extends SkillPoint {

    private final int data;

    public MobXP(String id, SkillType skillType, Double xp, int data) {
        super(id, skillType, xp);
        this.data = data;
    }
}
