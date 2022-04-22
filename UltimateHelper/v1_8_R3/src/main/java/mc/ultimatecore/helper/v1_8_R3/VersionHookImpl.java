package mc.ultimatecore.helper.v1_8_R3;

import mc.ultimatecore.helper.*;
import mc.ultimatecore.helper.v1_8_R3.hooks.mythiclib.*;
import mc.ultimatecore.helper.v1_8_R3.hooks.worldedit.WorldEditImpl;

public class VersionHookImpl implements VersionHook {

    private final WorldEditImpl worldEdit = new WorldEditImpl();

    private final MythicLibImpl mythicLib = new MythicLibImpl();

    @Override
    public WorldEditImpl getWorldEdit() {
        return worldEdit;
    }

    @Override
    public MythicLibImpl getMythicLib() {
        return mythicLib;
    }
}
