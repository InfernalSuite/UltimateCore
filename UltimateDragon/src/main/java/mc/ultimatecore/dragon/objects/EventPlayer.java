package mc.ultimatecore.dragon.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class EventPlayer {
    private final UUID uuid;
    private double damage;
    private final String name;

    public EventPlayer(UUID uuid, double damage){
        this.uuid = uuid;
        this.damage = damage;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public String getName(){
        return name;
    }

}
