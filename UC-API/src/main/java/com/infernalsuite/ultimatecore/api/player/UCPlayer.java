package com.infernalsuite.ultimatecore.api.player;

import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

/**
 * A Player representation within UltimateCore
 */
public interface UCPlayer {

    /**
     * Gets the users unique ID.
     * @return the user's UUID
     */
    @NonNull UUID getUniqueId();

}
