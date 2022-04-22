package mc.ultimatecore.helper;
import mc.ultimatecore.helper.hooks.mythiclib.*;
import mc.ultimatecore.helper.hooks.worldedit.*;

public interface VersionHook {

    WorldEdit getWorldEdit();

    MythicLib getMythicLib();

}