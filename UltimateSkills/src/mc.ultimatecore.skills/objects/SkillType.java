package mc.ultimatecore.skills.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SkillType {
    Mining(),
    Farming(),
    Combat(),
    Foraging(),
    Fishing(),
    Enchanting(),
    Alchemy(),
    Carpentry(),
    Runecrafting(),
    Social(),
    Taming(),
    Dungeoneering();

    private final String name;

    SkillType() {
        this.name = toString();
    }

    public static List<String> getSkills(){
        return new ArrayList<String>(){{
            Arrays.stream(SkillType.values()).forEach(skillType -> {
                add(skillType.getName()+"_LEVEL int");
                add(skillType.getName()+"_XP double");
            });
        }};
    }


    public String getName() {
        return this.name;
    }

    public String getLowName() {
        return this.name.toLowerCase();
    }

}
