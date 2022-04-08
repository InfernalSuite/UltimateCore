package com.infernalsuite.ultimatecore.common.manager.player;

import com.infernalsuite.ultimatecore.common.manager.*;
import com.infernalsuite.ultimatecore.common.player.*;

import java.util.*;
import java.util.concurrent.*;

public interface UCPlayerManager<T extends UCPlayerImpl> extends Manager<UUID, UCPlayerImpl, T> {

    T getOrMake(UUID id);

    /**
     * Gets a player object by name.
     * @param name the name to search by
     * @return a {@link UCPlayerImpl} object if the player is loaded, or {@code null}
     */
    T getByUsername(String name);

    /**
     * Gets the housekeeper, responsible unloading unneeded players.
     * @return the housekeeper
     */
    PlayerHousekeeper getHousekeeper();

    /**
     * Reloads the data of all online players.
     */
    CompletableFuture<Void> loadAllPlayers();

    /**
     * Invalidates the cached data for loaded players.
     */
    void invalidateAllPlayerCaches();

}
