package com.infernalsuite.ultimatecore.skills.objects;

import com.infernalsuite.ultimatecore.skills.implementations.Levellable;

import java.util.HashMap;
import java.util.Map;

;

public class PlayerSkills implements Levellable {

    private final Map<SkillType, Integer> playerLevel = new HashMap<>();

    private final Map<SkillType, Double> playerXP = new HashMap<>();

    private transient final Map<SkillType, Integer> rankPosition = new HashMap<>();

    public PlayerSkills() {
        for (SkillType skill : SkillType.values()) {
            playerLevel.put(skill, 0);
            playerXP.put(skill, 0D);
            rankPosition.put(skill, 1);
        }
    }

    @Override
    public void addXP(SkillType skill, Double quantity) {
        if (playerXP.containsKey(skill))
            playerXP.put(skill, playerXP.get(skill) + quantity);
    }

    @Override
    public void removeXP(SkillType skill, Double quantity) {
        if (playerXP.containsKey(skill))
            playerXP.put(skill, playerXP.get(skill) - quantity);
    }

    @Override
    public void addLevel(SkillType skill, Integer quantity) {
        if (playerLevel.containsKey(skill))
            playerLevel.put(skill, playerLevel.get(skill) + quantity);
    }

    @Override
    public void removeLevel(SkillType skill, Integer quantity) {
        if (playerLevel.containsKey(skill))
            playerLevel.put(skill, playerLevel.get(skill) - quantity);
    }

    @Override
    public int getLevel(SkillType skill) {
        return playerLevel.getOrDefault(skill, 0);
    }

    @Override
    public Double getXP(SkillType skill) {
        return playerXP.getOrDefault(skill, 0d);
    }

    @Override
    public void setLevel(SkillType skill, Integer level) {
        if (playerLevel.containsKey(skill))
            playerLevel.put(skill, level);
    }

    @Override
    public void setXP(SkillType skill, Double xp) {
        if (playerXP.containsKey(skill))
            playerXP.put(skill, xp);
    }

    public double getSkillValue(SkillType skillType){
        return playerLevel.get(skillType)+playerXP.get(skillType);
    }

    public int getRankPosition(SkillType skillType){
        if(rankPosition.containsKey(skillType))
            return rankPosition.get(skillType);
        return -1;
    }

    public void setRankPosition(SkillType skillType, int value){
        if(rankPosition.containsKey(skillType))
            rankPosition.put(skillType, value);
    }
}
