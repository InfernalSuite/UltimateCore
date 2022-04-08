package com.infernalsuite.ultimatecore.common.util;

import com.github.benmanes.caffeine.cache.*;
import lombok.experimental.*;

import java.util.concurrent.*;

@UtilityClass
public class CaffeineFactory {

    private final ForkJoinPool loaderPool = new ForkJoinPool();

    public Caffeine<Object, Object> newBuilder() { return Caffeine.newBuilder().executor(loaderPool); }

    public Executor executor() { return loaderPool; }

}
