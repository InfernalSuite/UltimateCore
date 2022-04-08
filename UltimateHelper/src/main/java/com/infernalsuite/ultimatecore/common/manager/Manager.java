package com.infernalsuite.ultimatecore.common.manager;

import java.util.*;
import java.util.function.*;

/**
 * A class which can manage instances of a class
 * @param <I> the identifying type for each object held in this manager
 * @param <C> the super class being managed
 * @param <T> the implementation class being managed
 */
public interface Manager<I, C, T> extends Function<I, T> {

    /**
     * Gets a map containing all cached instances held by this manager.
     * @return all instances held in this manager
     */
    Map<I, T> getAll();

    /**
     * Gets or creates an object by id.
     * @apiNote Should only ever be called by the storage implementation
     * @param id the id to search by
     * @return a {@link T} object if loaded, or makes and returns a new object
     */
    T getOrMake(I id);

    /**
     * Gets an object by id.
     * @param id the id to search by
     * @return a {@link T} object if loaded, or {@code null}
     */
    T getIfLoaded(I id);

    /**
     * Check to see if an object is loaded or not.
     * @param id the id to search by
     * @return {@code true} if the object is loaded
     */
    boolean isLoaded(I id);

    /**
     * Unloads the object from the manager
     * @param id the id of the object to unload
     */
    void unload(I id);

    /**
     * Calls {@link #unload(Object)} for all objects currently loaded and not in the given collection of ids.
     * @param ids the ids to retain
     */
    void retainAll(Collection<I> ids);

}
