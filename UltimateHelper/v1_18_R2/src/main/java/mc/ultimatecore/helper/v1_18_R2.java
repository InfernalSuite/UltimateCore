package mc.ultimatecore.helper;

import mc.ultimatecore.helper.hooks.worldedit.*;

public class v1_18_R2 implements VersionHook {

    private final WorldEditImpl worldEdit = new WorldEditImpl();

    @Override
    public WorldEditImpl getWorldEdit() {
        return worldEdit;
    }
}
