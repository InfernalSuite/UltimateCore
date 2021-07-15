package mc.ultimatecore.anvil.enums;

import mc.ultimatecore.anvil.HyperAnvil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum AnvilState {

    INCOMPATIBLE_ITEMS(HyperAnvil.getInstance().getMessages().getDescription("incompatibleItems")),
    INCOMPATIBLE_ENCHANTMENTS(HyperAnvil.getInstance().getMessages().getDescription("incompatibleEnchantments")),
    NO_ERROR_TAG(Collections.emptyList()),
    NO_ERROR_BOOK(new ArrayList<>()),
    NO_ERROR_ITEMS(new ArrayList<>());

    public final List<String> description;

    AnvilState(List<String> description){
        this.description = description;
    }

}
