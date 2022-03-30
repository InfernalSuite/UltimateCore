package com.infernalsuite.ultimatecore.skills.managers;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.DataReset;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ResetDataManager {
    private final HyperSkills plugin;
    private final Map<UUID, DataReset> players = new HashMap<>();

    public boolean addPlayer(UUID uuid, UUID executor){
        if(players.containsKey(uuid)) return false;
        players.put(uuid, new DataReset(uuid, executor));
        return true;
    }

    public void removePlayer(UUID uuid, boolean completed){
        if(!players.containsKey(uuid)) return;
        if(completed)
            players.get(uuid).stop(true);
        players.remove(uuid);
    }

    public void resetData(UUID uuid){
        removePlayer(uuid, true);
        plugin.getAbilitiesManager().resetData(uuid);
        plugin.getPerksManager().resetData(uuid);
        plugin.getSkillManager().resetData(uuid);
    }
}
