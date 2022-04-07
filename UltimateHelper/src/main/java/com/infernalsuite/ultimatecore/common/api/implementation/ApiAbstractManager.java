package com.infernalsuite.ultimatecore.common.api.implementation;

import mc.ultimatecore.helper.*;

/**
 * An abstraction of a UC API manager
 * @param <I> The Internal Type the manager is responsible for
 * @param <E> The API Type the manager is responsible for
 * @param <H> The Handle Type for the manager
 */
public abstract class ApiAbstractManager<I, E, H> {

    protected final UltimatePlugin plugin;
    protected final H handle;

    protected ApiAbstractManager(UltimatePlugin plugin, H handle) {
        this.plugin = plugin;
        this.handle = handle;
    }

    protected abstract E proxy(I internal);

}
