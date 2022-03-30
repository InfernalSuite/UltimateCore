package com.infernalsuite.ultimatecore.dragon.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class DragonPlayer {
    private final UUID uuid;
    @Setter
    private double record;
}
