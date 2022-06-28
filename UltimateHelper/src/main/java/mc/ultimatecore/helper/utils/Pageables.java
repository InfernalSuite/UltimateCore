package mc.ultimatecore.helper.utils;

import lombok.*;
import mc.ultimatecore.helper.utils.impl.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pageables {

    public static Pageable of(int page, int itemsPerPage) {
        return new PageableImpl(page, itemsPerPage);
    }
}
