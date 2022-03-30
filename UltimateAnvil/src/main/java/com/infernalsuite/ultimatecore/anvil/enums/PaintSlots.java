package com.infernalsuite.ultimatecore.anvil.enums;

public enum PaintSlots {

    ABOVE_LEFT(new Integer[]{20, 11 ,12}),

    ABOVE_RIGHT(new Integer[]{24, 15 ,14}),

    BELOW(new Integer[]{45, 46, 47, 48, 50, 51, 52, 53});

    private final Integer[] slots;

    PaintSlots(Integer[] slots) {
        this.slots = slots;
    }

    public Integer[] getSlots() {
        return this.slots;
    }
}
