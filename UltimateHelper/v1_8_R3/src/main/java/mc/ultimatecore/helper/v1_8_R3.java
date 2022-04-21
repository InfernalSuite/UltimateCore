package mc.ultimatecore.helper;

import mc.ultimatecore.helper.hooks.worldedit.WorldEditImpl;

public class v1_8_R3 implements VersionHook {

    private final WorldEditImpl worldEdit = new WorldEditImpl();

    @Override
    public WorldEditImpl getWorldEdit() {
        return worldEdit;
    }
}
