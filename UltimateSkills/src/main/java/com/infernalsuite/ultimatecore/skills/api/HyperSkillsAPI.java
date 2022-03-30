package com.infernalsuite.ultimatecore.skills.api;

import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;

import java.util.UUID;

public interface HyperSkillsAPI {

    /**
     * Method to get Player level for the specified skill
     *
     * @param skill SkillType
     * @param uuid UUID
     * @return return skill level
     */
    int getLevel(UUID uuid, SkillType skill);

    /**
     * Method to set Player level for a specific skill
     *
     * @param skill SkillType
     * @param uuid UUID
     * @param level int
     */
    void setLevel(UUID uuid, SkillType skill, int level);

    /**
     * Method to add Levels to a specific player skill
     *
     * @param skill SkillType
     * @param uuid UUID
     * @param level int
     * @return return skill level
     */
    void addLevel(UUID uuid, SkillType skill, int level);

    /**
     * Method to get Player XP for the specified skill
     * @param skill SkillType
     * @param uuid UUID
     * @return return skill xp
     */
    double getXP(UUID uuid, SkillType skill);

    /**
     * Method to set Player XP for the specified skill
     * @param skill SkillType
     * @param uuid UUID
     * @param xp double
     */
    void setXP(UUID uuid, SkillType skill, double xp);

    /**
     * Method to add XP specified player skill
     * @param skill SkillType
     * @param uuid UUID
     * @param xp double
     */
    void addXP(UUID uuid, SkillType skill, double xp);

    /**
     * Method to add more ability to the player
     * @param ability Ability
     * @param uuid UUID
     * @param quantity double
     */
    void addArmorAbility(UUID uuid, Ability ability, double quantity);

    /**
     * Method to set a remove quantity from an ability
     * @param ability Ability
     * @param uuid UUID
     * @param quantity double
     */
    void removeArmorAbility(UUID uuid, Ability ability, double quantity);

    /**
     * Method to add more perk to the player
     * @param perk Perk
     * @param uuid UUID
     * @param quantity double
     */
    void addArmorPerk(UUID uuid, Perk perk, double quantity);

    /**
     * Method to set a Perk quantity from an perk
     * @param perk Ability
     * @param uuid UUID
     * @param quantity double
     */
    void removeArmorPerk(UUID uuid, Perk perk, double quantity);

    /**
     * Method to add more ability to the player
     * @param ability Ability
     * @param uuid UUID
     * @param quantity double
     */
    void addAbility(UUID uuid, Ability ability, double quantity);

    /**
     * Method to set a specific quantity to an ability
     * @param ability Ability
     * @param uuid UUID
     * @param quantity double
     */
    void setAbility(UUID uuid, Ability ability, double quantity);

    /**
     * Method to set a remove quantity from an ability
     * @param ability Ability
     * @param uuid UUID
     * @param quantity double
     */
    void removeAbility(UUID uuid, Ability ability, double quantity);

    /**
     * Method to get Total Player Ability quantity
     * @param ability Ability
     * @param uuid UUID
     * @return return ability quantity
     */
    double getTotalAbility(UUID uuid, Ability ability);

    /**
     * Method to get Player Ability quantity
     * @param ability Ability
     * @param uuid UUID
     * @return return ability quantity
     */
    double getSimpleAbility(UUID uuid, Ability ability);

    /**
     * Method to get Player extra Ability quantity
     * @param ability Ability
     * @param uuid UUID
     * @return return ability quantity
     */
    double getExtraAbility(UUID uuid, Ability ability);

    /**
     * Method to add more ability to the player
     * @param perk Perk
     * @param uuid UUID
     * @param quantity double
     */
    void addPerk(UUID uuid, Perk perk, double quantity);

    /**
     * Method to set a specific quantity to a perk
     * @param perk Perk
     * @param uuid UUID
     * @param quantity double
     */
    void setPerk(UUID uuid, Perk perk, double quantity);

    /**
     * Method to a remove a specific quantity from a perk
     * @param perk Perk
     * @param uuid UUID
     * @param quantity double
     */
    void removePerk(UUID uuid, Perk perk, double quantity);

    /**
     * Method to get Total Player Perk quantity
     * @param perk Perk
     * @param uuid UUID
     * @return return perk quantity
     */
    double getTotalPerk(UUID uuid, Perk perk);

    /**
     * Method to get Simple Player Perk quantity
     * @param perk Perk
     * @param uuid UUID
     * @return return perk quantity
     */
    double getSimplePerk(UUID uuid, Perk perk);

    /**
     * Method to get Extra Player Perk quantity
     * @param perk Perk
     * @param uuid UUID
     * @return return perk quantity
     */
    double getExtraPerk(UUID uuid, Perk perk);

    /**
     * Method to get Player Skill rank
     * @param uuid UUID
     * @param skillType SkillType
     * @return return skill rank
     */
    double getRank(UUID uuid, SkillType skillType);
}
