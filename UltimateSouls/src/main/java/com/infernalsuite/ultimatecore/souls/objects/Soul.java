package com.infernalsuite.ultimatecore.souls.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Soul {
    
    private final int id;
    
    private Location location;
    
    private List<String> commandRewards;
    
    private double moneyReward;
    
    private SoulParticle particle;
    
    public void addCommandReward(String reward) {
        if (commandRewards == null) commandRewards = new ArrayList<>();
        this.commandRewards.add(reward);
    }
    
    public void removeCommandReward(String reward) {
        if (commandRewards == null) commandRewards = new ArrayList<>();
        this.commandRewards.remove(reward);
    }
}
