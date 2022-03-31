package mc.ultimatecore.dragon.implementations;


import lombok.Getter;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;

public abstract class SoftDependImpl {
    @Getter
    private final String displayName;

    public SoftDependImpl(String displayName){
        this.displayName = displayName;
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e["+ HyperDragons.getInstance().getDescription().getName() +"] &aSuccessfully hooked into "+displayName+"!"));
    }
}
