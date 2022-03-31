package mc.ultimatecore.dragon.implementations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.dragon.HyperDragons;

@RequiredArgsConstructor
@Getter
public abstract class CoreManagerImpl {
    protected final HyperDragons plugin;
    public void load(){}
    public void save(){}
}
