package com.infernalsuite.ultimatecore.common.storage.implementation;

import com.infernalsuite.ultimatecore.common.player.*;
import mc.ultimatecore.helper.*;

import java.util.*;

public interface StorageImplementation {

    UltimatePlugin getPlugin();

    String getImplementationName();

    void init() throws Exception;

    void shutdown();

    UCPlayerImpl loadPlayer(UUID uuid, String username) throws Exception;

    Map<UUID, UCPlayerImpl> loadPlayers(Set<UUID> uuids) throws Exception;

    void savePlayer(UCPlayerImpl player) throws Exception;

}
