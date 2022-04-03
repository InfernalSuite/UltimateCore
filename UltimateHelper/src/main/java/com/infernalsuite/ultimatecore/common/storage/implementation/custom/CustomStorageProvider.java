package com.infernalsuite.ultimatecore.common.storage.implementation.custom;

import com.infernalsuite.ultimatecore.common.storage.implementation.*;
import mc.ultimatecore.helper.*;

@FunctionalInterface
public interface CustomStorageProvider {

    StorageImplementation provide(UltimatePlugin plugin);

}
