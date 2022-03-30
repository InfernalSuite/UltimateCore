package com.infernalsuite.ultimatecore.crafting.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class StatsMap {
    private final Map<String, Integer> unlockedPlayer;
    private final Map<String, Integer> totalAmount;
}
