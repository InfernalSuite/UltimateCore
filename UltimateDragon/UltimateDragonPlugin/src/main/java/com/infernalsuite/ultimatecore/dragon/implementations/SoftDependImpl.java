package com.infernalsuite.ultimatecore.dragon.implementations;


import lombok.Getter;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;

public abstract class SoftDependImpl {
    @Getter
    private final String displayName;

    public SoftDependImpl(String displayName){
        this.displayName = displayName;
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e["+ HyperDragons.getInstance().getDescription().getName() +"] &aSuccessfully hooked into "+displayName+"!"));
    }
}
