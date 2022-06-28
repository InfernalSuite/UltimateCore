package mc.ultimatecore.helper.utils.impl;

import lombok.*;
import mc.ultimatecore.helper.utils.*;

@Data
public class PageableImpl implements Pageable {
    private final int page;
    private final int itemsPerPage;

    public PageableImpl(int page, int itemsPerPage) {

        this.page = page;
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public int getOffset() {
        return page * itemsPerPage;
    }

    @Override
    public int getMaxPages(int total) {
        return (int) Math.ceil(total/(float)itemsPerPage);
    }
}
