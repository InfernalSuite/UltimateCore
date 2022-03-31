package mc.ultimatecore.reforge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaintSlots {
    ALL_SLOTS(new Integer[]{0,9,18,27,36,8,17,26,35,44});

    private final Integer[] slots;
}
