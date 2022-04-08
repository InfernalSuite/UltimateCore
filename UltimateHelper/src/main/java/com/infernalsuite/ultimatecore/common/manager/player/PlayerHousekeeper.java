package com.infernalsuite.ultimatecore.common.manager.player;

import com.infernalsuite.ultimatecore.common.player.*;
import com.infernalsuite.ultimatecore.common.util.*;
import mc.ultimatecore.helper.*;

import java.util.*;
import java.util.concurrent.*;

public class PlayerHousekeeper implements Runnable {

    private final UltimatePlugin plugin;
    private final UCPlayerManager<?> playerManager;

    private final ExpiringSet<UUID> recentlyUsed;

    private final ExpiringSet<UUID> recentlyUsedApi;

    public PlayerHousekeeper(UltimatePlugin plugin, UCPlayerManager<?> playerManager, TimeoutSettings timeoutSettings) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.recentlyUsed = new ExpiringSet<>(timeoutSettings.duration, timeoutSettings.unit);
        this.recentlyUsedApi = new ExpiringSet<>(5, TimeUnit.MINUTES);
    }

    public void registerUsage(UUID uuid) {
        this.recentlyUsed.add(uuid);
    }

    public void registerApiUsage(UUID uuid) {
        this.recentlyUsedApi.add(uuid);
    }

    public void clearApiUsage(UUID uuid) {
        this.recentlyUsedApi.remove(uuid);
    }

    @Override
    public void run() {
        this.playerManager.getAll().keySet().forEach(this::cleanup);
    }

    public void cleanup(UUID uuid) {
        if (this.recentlyUsed.contains(uuid) || this.recentlyUsedApi.contains(uuid) || this.plugin.isPlayerOnline(uuid)) {
            return;
        }

        UCPlayerImpl player = this.playerManager.getIfLoaded(uuid);
        if (player == null) return;

        this.playerManager.unload(uuid);
    }

    public static TimeoutSettings timeoutSettings(long duration, TimeUnit unit) {
        return new TimeoutSettings(duration, unit);
    }

    public static final class TimeoutSettings {
        private final long duration;
        private final TimeUnit unit;

        TimeoutSettings(long duration, TimeUnit unit) {
            this.duration = duration;
            this.unit = unit;
        }
    }

}
