package mc.ultimatecore.alchemy.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
public enum BrewingSlots {
    //PRIVATE MENU
    CREATOR_DECORATION_SLOTS(new ArrayList<>(Arrays.asList(0,1,9,7,8,17,27,36,37,45,46,47,35,43,44,53,52,51))),

    //BREWING MENU
    AVAILABLE_SLOTS(new ArrayList<>(Arrays.asList(13,38,40,42))),
    BOTTLE_SLOTS(new ArrayList<>(Arrays.asList(38,40,42))),
    EFFECT_SLOTS(new ArrayList<>(Arrays.asList(20,21,22,23,24,29,31,33))),

    EFFECT_ONE(new ArrayList<>(Arrays.asList(22, 21, 31, 20, 29))),
    EFFECT_TWO(new ArrayList<>(Arrays.asList(22, 23, 31, 24, 33)));

    @Getter
    private final ArrayList<Integer> slots;

}
