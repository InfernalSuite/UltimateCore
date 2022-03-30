package mc.ultimatecore.skills.objects.abilities;

import lombok.Getter;
import mc.ultimatecore.skills.objects.SkillType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Ability {

    Defense(SkillType.Mining, "DEFENSE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjYyY2ZhOWNkMDY4NTZjZDk0N2VhY2FiNzBjYjQ1OWUzYjE3YTIxY2E0NDc1NTVhYmNiNzczOWJlN2Y1M2UzMiJ9fX0="),
    Strength(SkillType.Foraging, "PVE_DAMAGE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMwNWJmYWU5OTVlYWRhMzRkMTQ5NjhkZGJmYjYwMWI2YjIyYzU5NzViNTE3NmU2MWNkMjIzZTMxZTNhZWY4MCJ9fX0="),
    Intelligence(null, "MANA", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjE2Y2M1NzU1Y2RkMjYwZjdiNGI1YzFhMWYxZjNiZDMxODUxZmMxZDk4Yjc0NDM3YjJmYjRiZDZlYjhkMiJ9fX0="),
    Max_Intelligence(SkillType.Enchanting, "MAX_MANA", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjE2Y2M1NzU1Y2RkMjYwZjdiNGI1YzFhMWYxZjNiZDMxODUxZmMxZDk4Yjc0NDM3YjJmYjRiZDZlYjhkMiJ9fX0="),
    Speed(SkillType.Dungeoneering, "MOVEMENT_SPEED", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQyMGNkZjJhZjU3Y2M3NTc3NzNhY2UzZjE5MTVjYjcyYjU0MzJhMmZkYTMzNzNiMTY3OGY5OGJlYTdhYzcifX19"),
    Crit_Damage(SkillType.Combat, "CRITICAL_STRIKE_POWER", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U0ZjQ5NTM1YTI3NmFhY2M0ZGM4NDEzM2JmZTgxYmU1ZjJhNDc5OWE0YzA0ZDlhNGRkYjcyZDgxOWVjMmIyYiJ9fX0="),
    Crit_Chance(SkillType.Combat, "CRITICAL_STRIKE_CHANCE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGRhZmIyM2VmYzU3ZjI1MTg3OGU1MzI4ZDExY2IwZWVmODdiNzljODdiMjU0YTdlYzcyMjk2ZjkzNjNlZjdjIn19fQ=="),
    Health(SkillType.Fishing, "MAX_HEALTH", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThkYTdlMjU1ZTA5YzhiMzc4ZWM4NmMwYjkyMmZhODY0YzRiMTlkMGU1ZTVkYTRkOGM3M2MyYjU2OWMyMjUwMiJ9fX0="),
    Pet_Luck(SkillType.Taming, "PET_LUCK", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjFmZmFlZTcxMmM3ZDhkMTNjYjMxOWU3NTYxODk1YmJlNTU2YmM1MjE4ZDk1ZTM5MzQxODkzZmFkOWI0NGMxIn19fQ==");

    private final String name;

    private final SkillType skill;

    private final String texture;

    private final String mmoItems;

    Ability(SkillType skill, String mmoItems, String texture) {
        this.name = toString();
        this.skill = skill;
        this.texture = texture;
        this.mmoItems = mmoItems;
    }

    public static List<String> getAbilities(){
        return Arrays.stream(Ability.values()).map(Ability::getName).collect(Collectors.toList());
    }

    public String getLowName() {
        return this.name.toLowerCase();
    }

}
