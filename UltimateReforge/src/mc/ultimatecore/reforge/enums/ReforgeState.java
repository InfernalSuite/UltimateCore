package mc.ultimatecore.reforge.enums;

import mc.ultimatecore.reforge.HyperReforge;

import java.util.ArrayList;
import java.util.List;

public enum ReforgeState {

    INCOMPATIBLE_ITEMS(HyperReforge.getInstance().getMessages().getDescription("incompatibleItems"), null),
    NO_ERROR_BOW(new ArrayList<>(), ItemType.BOW),
    NO_ERROR_WEAPON(new ArrayList<>(), ItemType.WEAPONS),
    NO_ERROR_ARMOR(new ArrayList<>(), ItemType.ARMORS);

    public final List<String> description;
    public final ItemType itemType;

    ReforgeState(List<String> description, ItemType itemType){
        this.description = description;
        this.itemType = itemType;
    }

}
