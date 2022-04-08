package com.infernalsuite.ultimatecore.common.cache;

import com.google.common.collect.*;
import lombok.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

@RequiredArgsConstructor
public class LoadingMap<K, V> extends ForwardingMap<K, V> implements Map<K, V> {

    public static <K, V> LoadingMap<K, V> of(Map<K, V> map, Function<K, V> function) {
        return new LoadingMap<>(map, function);
    }

    public static <K, V> LoadingMap<K, V> of(Function<K, V> function) {
        return of(new ConcurrentHashMap<>(), function);
    }

    private final Map<K, V> map;
    private final Function<K, V> function;

    @Override
    protected @NotNull Map<K, V> delegate() {
        return this.map;
    }

    public V getIfPresent(K key) { return this.map.get(key); }

    @Override
    public V get(@Nullable Object key) {
        V value = this.map.get(key);
        if (value != null) return value;
        //noinspection unchecked
        return this.map.computeIfAbsent((K) key, this.function);
    }
}
