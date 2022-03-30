package com.infernalsuite.ultimatecore.talismans.implementations;

import com.infernalsuite.ultimatecore.talismans.utils.StringUtils;
import lombok.Getter;
import org.bukkit.Bukkit;

public abstract class SoftDependImpl {
    @Getter
    private final String displayName;

    public SoftDependImpl(String displayName){
        this.displayName = displayName;
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperSkills] &aSuccessfully hooked into "+displayName+"!"));
    }
}
