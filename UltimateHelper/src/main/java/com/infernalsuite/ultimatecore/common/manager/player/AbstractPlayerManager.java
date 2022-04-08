package com.infernalsuite.ultimatecore.common.manager.player;

import com.infernalsuite.ultimatecore.common.manager.*;
import com.infernalsuite.ultimatecore.common.player.*;
import com.infernalsuite.ultimatecore.common.util.*;
import lombok.*;
import mc.ultimatecore.helper.*;

import java.util.*;
import java.util.concurrent.*;

public abstract class AbstractPlayerManager<T extends UCPlayerImpl> extends AbstractManager<UUID, UCPlayerImpl, T> implements UCPlayerManager<T> {

    private final UltimatePlugin plugin;
    @Getter
    private final PlayerHousekeeper housekeeper;

    public AbstractPlayerManager(UltimatePlugin plugin, PlayerHousekeeper.TimeoutSettings timeoutSettings) {
        this.plugin = plugin;
        this.housekeeper = new PlayerHousekeeper(plugin, this, timeoutSettings);
        this.plugin.getSchedulerAdapter().asyncRepeating(this.housekeeper, 30, TimeUnit.SECONDS);
    }

    @Override
    public T getOrMake(UUID id, String username) {
        T user = getOrMake(id);
        if (username != null) {
            user.setUsername(username, false);
        }
        return user;
    }

    @Override
    public T getByUsername(String name) {
        for (T user: getAll().values()) {
            Optional<String> optName = user.getUsername();
            if (optName.isPresent() && optName.get().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public CompletableFuture<Void> loadAllPlayers() {
        Set<UUID> uuids = new HashSet<>(getAll().keySet());
        uuids.addAll(this.plugin.getOnlinePlayers());

        return uuids.stream()
                .map(uuid -> this.plugin.getStorage().loadPlayer(uuid, null))
                .collect(CompletableFutures.collector());
    }

    @Override
    public void invalidateAllPlayerCaches() {
        // TODO
    }
}
