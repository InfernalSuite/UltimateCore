package mc.ultimatecore.helper.utils;

public interface Pageable {
    int getPage();
    int getItemsPerPage();
    int getOffset();

    int getMaxPages(int total);
}
