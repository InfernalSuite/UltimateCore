package com.infernalsuite.ultimatecore.runes.runetable;

public enum PaintSlots {

    ABOVE_LEFT(new Integer[]{10, 11 ,12}),

    ABOVE_RIGHT(new Integer[]{16, 15 ,14}),

    BELOW_LEFT(new Integer[]{45, 46, 47, 48}),

    BELOW_RIGHT(new Integer[]{53, 52, 51, 50}),


    BELOW(new Integer[]{45, 46, 47, 48, 50, 51, 52, 53, 22}),


    EFFECT_ONE(new Integer[]{10, 45, 11, 46, 12, 47, 22, 48}),
    EFFECT_TWO(new Integer[]{16, 53, 15, 52, 14, 51, 22, 50}),


    ALL_SLOTS(new Integer[]{10, 11, 12, 14, 15, 16, 22, 45, 46, 47, 48, 50, 51, 52, 53});




    private final Integer[] slots;

    PaintSlots(Integer[] slots) {
        this.slots = slots;
    }

    public Integer[] getSlots() {
        return this.slots;
    }
}
