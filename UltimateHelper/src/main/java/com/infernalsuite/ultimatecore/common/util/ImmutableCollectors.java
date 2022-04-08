package com.infernalsuite.ultimatecore.common.util;

import com.google.common.collect.*;
import lombok.experimental.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@UtilityClass
public class ImmutableCollectors {

    private final Collector<Object, ImmutableList.Builder<Object>, ImmutableList<Object>> LIST = Collector.of(
            ImmutableList.Builder::new,
            ImmutableList.Builder::add,
            (l, r) -> l.addAll(r.build()),
            ImmutableList.Builder::build
    );

    private final Collector<Object, ImmutableSet.Builder<Object>, ImmutableSet<Object>> SET = Collector.of(
            ImmutableSet.Builder::new,
            ImmutableSet.Builder::add,
            (l, r) -> l.addAll(r.build()),
            ImmutableSet.Builder::build
    );

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toList() {
        return (Collector) LIST;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> toSet() {
        return (Collector) SET;
    }

    public <T extends Enum<T>> Collector<T, EnumSet<T>, ImmutableSet<T>> toEnumSet(Class<T> clazz) {
        return Collector.of(
                () -> EnumSet.noneOf(clazz),
                EnumSet::add,
                (l, r) -> { l.addAll(r); return l; },
                ImmutableSet::copyOf
        );
    }

    public <E extends Comparable<? super E>> Collector<E, ?, ImmutableSortedSet<E>> toSortedSet() {
        return Collector.of(
                ImmutableSortedSet::<E>naturalOrder,
                ImmutableSortedSet.Builder::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableSortedSet.Builder::build
        );
    }

    public <E> Collector<E, ?, ImmutableSortedSet<E>> toSortedSet(Comparator<? super E> comparator) {
        return Collector.of(
                () -> new ImmutableSortedSet.Builder<E>(comparator),
                ImmutableSortedSet.Builder::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableSortedSet.Builder::build
        );
    }

    public <T, K, V> Collector<T, ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                ImmutableMap.Builder<K, V>::new,
                (r, t) -> r.put(keyMapper.apply(t), valueMapper.apply(t)),
                (l, r) -> l.putAll(r.build()),
                ImmutableMap.Builder::build
        );
    }

    public <T, K extends Enum<K>, V> Collector<T, EnumMap<K, V>, ImmutableMap<K, V>> toEnumMap(Class<K> clazz, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                () -> new EnumMap<>(clazz),
                (r, t) -> r.put(keyMapper.apply(t), valueMapper.apply(t)),
                (l, r) -> { l.putAll(r); return l; },
                ImmutableMap::copyOf
        );
    }

}