package com.infernalsuite.ultimatecore.skills.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class RankPlayer {
    private final UUID uuid;
    private final SkillType skillType;
    private final double points;
}
