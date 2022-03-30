package com.infernalsuite.ultimatecore.dragon.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import org.bukkit.Bukkit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskUtilities {
    
    public static void runSync(Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
            return;
        }
        Bukkit.getScheduler().runTask(HyperDragons.getInstance(), runnable);
    }
}