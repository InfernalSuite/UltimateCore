package com.infernalsuite.ultimatecore.api;

import org.checkerframework.checker.nullness.qual.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * Provides static access to the {@link UltimateCoreAPI}.
 */
public class UCAPIProvider {

    /**
     * The UC API instance.
     */
    private static UltimateCoreAPI instance = null;

    private static final Map<Class<? extends UltimateCoreAPI>, UltimateCoreAPI> registeredAPIs = new HashMap<>();

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

    /**
     * Gets an instance of an UltimateCore API, throwing {@link IllegalStateException} if the requested API is not loaded yet.
     * <p>This method will never return null.</p>
     * @param clazz the API class
     * @param <T> the API type
     * @return an instance of the requested API
     * @throws IllegalStateException if the requested API is not loaded yet
     */
    @SuppressWarnings("unchecked")
    public static <T extends UltimateCoreAPI> @NonNull T get(Class<T> clazz) {
        UltimateCoreAPI api = registeredAPIs.get(clazz);
        if (api == null) throw new IllegalStateException("Requested API '" + clazz.getName() + "' is not loaded yet!");
        return (T) api;
    }

    @ApiStatus.Internal
    static void register(UltimateCoreAPI instance) {
        UCAPIProvider.instance = instance;
        registeredAPIs.put(UltimateCoreAPI.class, instance);
    }

    @ApiStatus.Internal
    static <T extends UltimateCoreAPI> void register(Class<T> clazz, T instance) {
        registeredAPIs.put(clazz, instance);
    }

    @ApiStatus.Internal
    static void unregister() {
        UCAPIProvider.instance = null;
        registeredAPIs.clear();
    }

    @ApiStatus.Internal
    static <T extends UltimateCoreAPI> void unregister(Class<T> clazz) {
        registeredAPIs.remove(clazz);
    }

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
