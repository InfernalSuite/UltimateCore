package com.infernalsuite.ultimatecore.common.storage;

import com.infernalsuite.ultimatecore.common.player.*;
import com.infernalsuite.ultimatecore.common.storage.implementation.*;
import com.infernalsuite.ultimatecore.common.util.*;
import lombok.*;
import mc.ultimatecore.helper.*;

import java.util.*;
import java.util.concurrent.*;

public class Storage {

    private final UltimatePlugin plugin;
    @Getter
    private final StorageImplementation implementation;

    public Storage(UltimatePlugin plugin, StorageImplementation implementation) {
        this.plugin = plugin;
        this.implementation = implementation;
    }

    private <T> CompletableFuture<T> future(Callable<T> callable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return callable.call();
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new CompletionException(e);
            }
        });
    }

    private CompletableFuture<Void> future(Throwing.Runnable runnable) {
        return CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new CompletionException(e);
            }
        });
    }

    public String getName() { return this.implementation.getImplementationName(); }

    public void init() {
        try {
            this.implementation.init();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Failed to init storage implementation");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            this.implementation.shutdown();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Failed to shutdown storage implementation");
            e.printStackTrace();
        }
    }

    public CompletableFuture<UCPlayerImpl> loadPlayer(UUID uuid, String username) {
        return future(() -> this.implementation.loadPlayer(uuid, username));
    }

    public CompletableFuture<Map<UUID, UCPlayerImpl>> loadPlayers(Set<UUID> uuids) {
        return future(() -> this.implementation.loadPlayers(uuids));
    }

    public CompletableFuture<Void> savePlayer(UCPlayerImpl player) {
        return future(() -> this.implementation.savePlayer(player));
    }

}
