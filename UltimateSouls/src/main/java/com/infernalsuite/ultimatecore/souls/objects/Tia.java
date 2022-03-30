package com.infernalsuite.ultimatecore.souls.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Tia {
    
    private final int soulsToClaim;
    private final List<String> messages;
    private final List<String> rewards;
}
