package com.infernalsuite.ultimatecore.common.storage.implementation;

import com.infernalsuite.ultimatecore.api.player.*;
import mc.ultimatecore.helper.*;

import java.util.*;

public interface StorageImplementation {

    UltimatePlugin getPlugin();

    String getImplementationName();

    void init() throws Exception;

    void shutdown();

    UCPlayer loadPlayer(UUID uuid, String username) throws Exception;

    Map<UUID, UCPlayer> loadPlayers(Set<UUID> uuids) throws Exception;

}
