package com.infernalsuite.ultimatecore.common.api.implementation;

import com.infernalsuite.ultimatecore.api.player.*;
import com.infernalsuite.ultimatecore.common.manager.player.*;
import com.infernalsuite.ultimatecore.common.player.*;
import com.infernalsuite.ultimatecore.common.util.*;
import mc.ultimatecore.helper.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class ApiPlayerManager extends ApiAbstractManager<UCPlayerImpl, UCPlayer, UCPlayerManager<?>> implements PlayerManager {

    public ApiPlayerManager(UltimatePlugin plugin, UCPlayerManager<?> handle) { super(plugin, handle); }

    @Override
    protected UCPlayer proxy(UCPlayerImpl internal) {
        return internal == null ? null : internal.getApiProxy();
    }

    private UCPlayer proxyAndRegisterUsage(UCPlayerImpl internal) {
        if (internal != null) {
            this.plugin.getPlayerManager().getHousekeeper().registerApiUsage(internal.getUuid());
        }
        return proxy(internal);
    }

    @Override
    public @NonNull CompletableFuture<UCPlayer> loadPlayer(@NonNull UUID uuid, @Nullable String username) {
        Objects.requireNonNull(uuid);
        return this.plugin.getStorage().loadPlayer(uuid, username).thenApply(this::proxyAndRegisterUsage);
    }

    @Override
    public @NonNull CompletableFuture<Void> savePlayer(@NonNull UCPlayer player) {
        UCPlayerImpl internal = ApiUCPlayer.cast(Objects.requireNonNull(player, "player cannot be null"));
        return this.plugin.getStorage().savePlayer(internal);
    }

    @Override
    public @NonNull CompletableFuture<Void> modifyPlayer(@NonNull UUID uuid, @NonNull Consumer<? super UCPlayer> action) {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        Objects.requireNonNull(action, "action cannot be null");

        return this.plugin.getStorage().loadPlayer(uuid, null)
                .thenApplyAsync(player -> {
                    action.accept(player.getApiProxy());
                    return player;
                }).thenCompose(player -> this.plugin.getStorage().savePlayer(player));
    }

    @Override
    public @Nullable UCPlayer getPlayer(@NonNull UUID uuid) {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        return proxyAndRegisterUsage(this.handle.getIfLoaded(uuid));
    }

    @Override
    public @Nullable UCPlayer getPlayer(@NonNull String username) {
        Objects.requireNonNull(username, "username cannot be null");
        return proxyAndRegisterUsage(this.handle.getByUsername(username));
    }

    @Override
    public @NonNull @Unmodifiable Set<UCPlayer> getLoadedUsers() {
        return this.handle.getAll().values().stream()
                .map(this::proxy)
                .collect(ImmutableCollectors.toSet());
    }

    @Override
    public boolean isLoaded(@NonNull UUID uuid) {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        return this.handle.isLoaded(uuid);
    }

    @Override
    public void cleanupUser(@NonNull UCPlayer player) {
        Objects.requireNonNull(player, "player cannot be null");
        this.handle.getHousekeeper().clearApiUsage(ApiUCPlayer.cast(player).getUuid());
    }
}
