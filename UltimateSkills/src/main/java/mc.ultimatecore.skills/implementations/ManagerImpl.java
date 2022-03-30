package mc.ultimatecore.skills.implementations;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.skills.HyperSkills;

@RequiredArgsConstructor
public abstract class ManagerImpl {
    protected final HyperSkills plugin;
    public abstract void load();
    public void save(){}
}
