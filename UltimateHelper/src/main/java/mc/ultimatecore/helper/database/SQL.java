package mc.ultimatecore.helper.database;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.helper.UltimatePlugin;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public abstract class SQL {
    
    protected static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);
    protected static final int MAXIMUM_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2) + 1;
    protected static final int MINIMUM_IDLE = Math.min(MAXIMUM_POOL_SIZE, 10);
    
    protected static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30);
    protected static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(10);
    protected static final long LEAK_DETECTION_THRESHOLD = TimeUnit.SECONDS.toMillis(10);
    
    protected final UltimatePlugin plugin;
}
