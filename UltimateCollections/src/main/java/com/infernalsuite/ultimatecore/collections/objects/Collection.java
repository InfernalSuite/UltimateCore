package com.infernalsuite.ultimatecore.collections.objects;

import java.util.HashMap;
import java.util.List;

public class Collection {
    
    private final String name;
    
    private final Category category;
    
    private final HashMap<Integer, List<String>> rewards;
    
    private final HashMap<Integer, Double> requirements;
    
    private final HashMap<Integer, List<String>> commands;
    
    private final String key;
    
    private final int slot;
    
    public Collection(String name, Category category, String key, int slot, HashMap<Integer, List<String>> rewards, HashMap<Integer, Double> requirements, HashMap<Integer, List<String>> commands) {
        this.name = name;
        this.category = category;
        this.rewards = rewards;
        this.requirements = requirements;
        this.commands = commands;
        this.key = key;
        this.slot = slot;
    }
    
    
    public int getSlot() {
        return this.slot;
    }
    
    public String getName() {
        return name;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public String getKey() {
        return key;
    }
    
    public List<String> getCommands(int level) {
        return commands.get(level);
    }
    
    public List<String> getRewards(int level) {
        return rewards.get(level);
    }
    
    public Double getRequirement(int level) {
        return requirements.get(level);
    }
    
    public int getMaxLevel() {
        return Math.min(requirements.size(), rewards.size());
    }
    
    public HashMap<Integer, Double> getRequirements() {
        return requirements;
    }
    
    public HashMap<Integer, List<String>> getCommands() {
        return commands;
    }
    
    public HashMap<Integer, List<String>> getRewards() {
        return rewards;
    }
}
