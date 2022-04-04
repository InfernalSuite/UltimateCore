package com.infernalsuite.ultimatecore.api.player;

import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

/**
 * A Player representation within UltimateCore
 */
public interface UCPlayer {

    /**
     * Gets the player's unique ID.
     * @return the player's UUID
     */
    @NonNull UUID getUniqueId();

    /**
     * Gets the player's username.
     * @apiNote returns {@code null} if no username is known for the player
     * @return the player's username
     */
    @Nullable String getUsername();

}
