package com.infernalsuite.ultimatecore.api;

import org.checkerframework.checker.nullness.qual.*;
import org.jetbrains.annotations.*;

/**
 * Provides static access to the {@link UltimateCoreAPI}.
 */
public class UCAPIProvider {

    /**
     * The UC API instance.
     */
    private static UltimateCoreAPI instance = null;

    /**
     * Gets an instance of the UltimateCore API, throwing {@link NotLoadedException} if the API
     * is not loaded yet.
     * <p>This method will never return null.</p>
     * @return an instance of the UltimateCore API
     * @throws NotLoadedException if the API is not loaded yet
     */
    public static @NonNull UltimateCoreAPI get() {
        UltimateCoreAPI instance = UCAPIProvider.instance;
        if (instance == null) throw new NotLoadedException();
        return instance;
    }

    @ApiStatus.Internal
    static void register(UltimateCoreAPI instance) { UCAPIProvider.instance = instance; }

    @ApiStatus.Internal
    static void unregister() { UCAPIProvider.instance = null; }

    /**
     * Exception to indicate an attempt was made to retrieve the API before it was loaded!
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = """
                The UltimateCore API isn't loaded yet!
                This could be because:
                  a) the UltimateCore plugin is not installed or it failed to enable
                  b) the plugin in the stacktrace does not declare a dependency on UltimateCore
                  c) the plugin in the stacktrace is retrieving the API before the plugin 'enable' phase
                     (call the #get method in onEnable, not the constructor!)
                """;
        NotLoadedException() {
            super(MESSAGE);
        }
    }

}
