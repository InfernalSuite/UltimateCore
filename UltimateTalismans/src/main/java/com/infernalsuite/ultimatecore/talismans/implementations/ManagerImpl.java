package com.infernalsuite.ultimatecore.talismans.implementations;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ManagerImpl {
    protected final HyperTalismans plugin;
    public abstract void load();
    public void save(){}
}
