package com.infernalsuite.ultimatecore.skills.implementations;

import com.infernalsuite.ultimatecore.skills.objects.SkillType;

public interface Levellable {
    void addXP(SkillType skill, Double xp);

    void removeXP(SkillType skill, Double xp);

    void addLevel(SkillType skill, Integer level);

    void removeLevel(SkillType skill, Integer level);

    void setLevel(SkillType skill, Integer level);

    void setXP(SkillType skill, Double xp);

    int getLevel(SkillType skill);

    Double getXP(SkillType skill);
}
