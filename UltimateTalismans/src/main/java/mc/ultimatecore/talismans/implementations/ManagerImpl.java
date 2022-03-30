package mc.ultimatecore.talismans.implementations;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.talismans.HyperTalismans;

@RequiredArgsConstructor
public abstract class ManagerImpl {
    protected final HyperTalismans plugin;
    public abstract void load();
    public void save(){}
}
