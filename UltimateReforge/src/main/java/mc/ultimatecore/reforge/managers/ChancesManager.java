package mc.ultimatecore.reforge.managers;

import lombok.Getter;
import mc.ultimatecore.reforge.enums.ItemType;
import mc.ultimatecore.reforge.objects.ChanceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ChancesManager {

    private final List<ChanceObject> chanceObjects;

    public ChancesManager(){
        this.chanceObjects = new ArrayList<>();
    }

    public Optional<ChanceObject> getChanceObject(ItemType itemType){
        return chanceObjects.stream().filter(chanceObject -> chanceObject.getItemType().equals(itemType)).findFirst();
    }

    public void addChance(ChanceObject chanceObject){
        chanceObjects.add(chanceObject);
    }
}
