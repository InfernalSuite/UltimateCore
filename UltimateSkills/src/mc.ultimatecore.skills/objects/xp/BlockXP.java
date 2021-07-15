package mc.ultimatecore.skills.objects.xp;

import lombok.Getter;
import mc.ultimatecore.skills.objects.SkillType;

@Getter
public class BlockXP extends SkillPoint{
    private final byte materialData;
    public BlockXP(String id, SkillType skillType, Double xp, byte materialData) {
        super(id, skillType, xp);
        this.materialData = materialData;
    }
}
