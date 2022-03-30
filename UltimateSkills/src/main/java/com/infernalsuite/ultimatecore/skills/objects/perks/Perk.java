package com.infernalsuite.ultimatecore.skills.objects.perks;

import lombok.Getter;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Perk {

    //PERKS
    Ore_Chance(SkillType.Mining, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk1NDNmMjc1NzUwMDNkZDVjODk4ZWFiN2JmZTRjM2RiYmU1NzA2NDgwODlmM2I5NzQwM2RmN2Y2YmYyYzFjYyJ9fX0="),
    Crop_Chance(SkillType.Farming, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FhNTk2NmExNDcyNDQ1MDRjYzU2ZWY2ZWZkMmQyZjQ0NzM4YjhmMDNkOTNhNjE3NjZhZjNmYzQ0ODdmOTgwYiJ9fX0="),
    Log_Chance(SkillType.Foraging, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEyYzM0MTQzYjljNzZhY2ZmY2M0MDAzN2I3MWQ3OGVhYTQ1ODRiNDYwNDUwN2IwN2VhMjRkOTMwYTM2Nzc0MyJ9fX0="),
    Exp_Chance(SkillType.Enchanting, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTg5NTBkMWEwYjhjY2YwNjNlNzUxOWZlMzY5Y2FkMTVjY2UzMjA1NmZhNDcyYTk5YWEzMGI1ZmI0NDhmZjYxNCJ9fX0="),
    Potions_Chance(SkillType.Alchemy, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDEyYWMyMzlmMzliYTkxN2U5YmQ2YzE5ZDZlN2RjNDgzMTc5NjUxMDQ3ODdjOGJmY2YwOTBjMGMwMzI3N2FjOSJ9fX0="),
    Sea_Creature_Chance(SkillType.Fishing, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzlkZWQzNmEwYmQ1ZmI2N2IyNmQwMTllNmVmZTc5Yjc3OTM1YTg5ZTI3YzNjMzAzMGUzMDk1YzA4NzJkZmMifX19"),
    Pet_Exp(SkillType.Taming, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjcyZTQ5ZDVlNGFmNDczNTcxZTdlMDc4YWI3OGMzZTgzOTY4ZDJmZTEwOWRhOTUyNDRjNWQwY2I3ZDIyYjQ2YiJ9fX0="),
    Mining_Speed(SkillType.Mining, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY3NDM1NGM3NTAzYzc3NDQ2NDNkY2JhNDA3NmM0NmY3YjAzMDczNDlmNTNjYjgxMmI5Yjk4NjdhNDUzYWMwYSJ9fX0=");

    private final String name;

    private final SkillType skill;

    private final String texture;

    Perk(SkillType skill, String texture) {
        this.name = toString();
        this.skill = skill;
        this.texture = texture;
    }

    public static List<String> getPerks(){
        return Arrays.stream(Perk.values()).map(Perk::getName).collect(Collectors.toList());
    }

    public String getLowName() {
        return this.name.toLowerCase();
    }

}
