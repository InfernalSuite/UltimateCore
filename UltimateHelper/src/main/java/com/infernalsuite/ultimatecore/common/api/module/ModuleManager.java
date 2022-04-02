package com.infernalsuite.ultimatecore.common.api.module;

import org.checkerframework.checker.nullness.qual.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public interface ModuleManager {

    /**
     * Loads the given Module.
     * @param module the module to load
     */
    void loadModule(@NonNull UCModule module);

    /**
     * Loads the module at the given path.
     * @param path the path to the module
     * @return the module
     * @throws IOException if the module could not be loaded
     */
    @NonNull UCModule loadModule(Path path) throws IOException;

    /**
     * Gets a collection of all loaded modules.
     * @return the loaded modules
     */
    @NonNull @Unmodifiable Collection<UCModule> getLoadedModules();

}
