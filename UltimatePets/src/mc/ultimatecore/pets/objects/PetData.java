package mc.ultimatecore.pets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetData {
    private Integer petUUID;
    private String petName;
    private int level;
    private double xp;
    private Tier tier;

    public PetData(int petUUID, String petName, Tier tier){
        this.petName = petName;
        this.petUUID = petUUID;
        this.level = 1;
        this.xp = 0;
        this.tier = tier;
    }

    public void addXP(double xp){
        this.xp+=xp;
    }
}
