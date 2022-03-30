package mc.ultimatecore.dragon.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mc.ultimatecore.dragon.HyperDragons;
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