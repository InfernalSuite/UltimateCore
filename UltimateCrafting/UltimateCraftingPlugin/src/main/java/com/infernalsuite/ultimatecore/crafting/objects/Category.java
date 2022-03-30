package com.infernalsuite.ultimatecore.crafting.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category {
    private final String key;
    private final String displayName;
    private final int slot;
    private final String material;
}
