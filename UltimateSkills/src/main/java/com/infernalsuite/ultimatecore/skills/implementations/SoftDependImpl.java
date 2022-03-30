package com.infernalsuite.ultimatecore.skills.implementations;

import lombok.Getter;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;

public abstract class SoftDependImpl {
    @Getter
    private final String displayName;

    public SoftDependImpl(String displayName){
        this.displayName = displayName;
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperSkills] &aSuccessfully hooked into "+displayName+"!"));
    }
}
