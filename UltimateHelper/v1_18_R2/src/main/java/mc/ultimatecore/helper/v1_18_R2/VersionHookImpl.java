package mc.ultimatecore.helper.v1_18_R2;

import mc.ultimatecore.helper.*;
import mc.ultimatecore.helper.v1_18_R2.hooks.worldedit.*;

public class VersionHookImpl implements VersionHook {

    private final WorldEditImpl worldEdit = new WorldEditImpl();

    @Override
    public WorldEditImpl getWorldEdit() {
        return worldEdit;
    }
}
