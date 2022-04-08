package com.infernalsuite.ultimatecore.common.manager;

import com.google.common.collect.*;
import com.infernalsuite.ultimatecore.common.cache.*;

import java.util.*;

/**
 * An abstract manager class
 * @param <I> the identifying type for each object held in this manager
 * @param <C> the super class being managed
 * @param <T> the implementation class being managed
 */
public abstract class AbstractManager<I, C, T extends C> implements Manager<I, C, T> {

    private final LoadingMap<I, T> objects = LoadingMap.of(this);

    @Override
    public Map<I, T> getAll() {
        return ImmutableMap.copyOf(this.objects);
    }

    @Override
    public T getOrMake(I id) {
        return this.objects.get(sanitiseIdentifier(id));
    }

    @Override
    public T getIfLoaded(I id) {
        return this.objects.getIfPresent(sanitiseIdentifier(id));
    }

    @Override
    public boolean isLoaded(I id) {
        return this.objects.containsKey(sanitiseIdentifier(id));
    }

    @Override
    public void unload(I id) {
        if (id != null) this.objects.remove(sanitiseIdentifier(id));
    }

    @Override
    public void retainAll(Collection<I> ids) {
        this.objects.keySet().stream()
                .filter(id -> !ids.contains(id))
                .forEach(this::unload);
    }

    protected I sanitiseIdentifier(I id) { return id; }

}
