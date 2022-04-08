package com.infernalsuite.ultimatecore.common.util;

import com.google.common.collect.*;
import lombok.experimental.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

@UtilityClass
public class CompletableFutures {

    public <T extends CompletableFuture<?>> Collector<T, ImmutableList.Builder<T>, CompletableFuture<Void>> collector() {
        return Collector.of(
                ImmutableList.Builder::new,
                ImmutableList.Builder::add,
                (l, r) -> l.addAll(r.build()),
                builder -> allOf(builder.build())
        );
    }

    public CompletableFuture<Void> allOf(Stream<? extends CompletableFuture<?>> futures) {
        CompletableFuture<?>[] arr = futures.toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(arr);
    }

    public CompletableFuture<Void> allOf(Collection<? extends CompletableFuture<?>> futures) {
        CompletableFuture<?>[] arr = futures.toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(arr);
    }

}
