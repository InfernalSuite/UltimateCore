package mc.ultimatecore.dragon.managers;

import lombok.Getter;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.DragonGame;
import mc.ultimatecore.dragon.objects.EventPlayer;
import mc.ultimatecore.dragon.objects.implementations.UCEnderDragon;
import mc.ultimatecore.dragon.objects.structures.DragonAltar;
import mc.ultimatecore.dragon.objects.structures.DragonStructure;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class DragonManager {

    @Getter
    private final Set<UUID> editorMode = new HashSet<>();

    @Getter
    private final DragonStructure dragonStructure;

    @Getter
    private final DragonGame dragonGame;



    public DragonManager(HyperDragons plugin){
        this.dragonStructure = plugin.getStructures().getDragonStructure();
        this.dragonGame = new DragonGame();
    }

    public boolean isActive(){
        return dragonGame.isActive();
    }

    public List<Entity> getCrystals(){
        return dragonGame.getCrystals();
    }

    public Set<Player> getEventPlayers(){
        return dragonGame.getEventPlayers().stream().map(EventPlayer::getPlayer).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public void setLast(UUID uuid){
        dragonGame.setLast(uuid);
    }

    public void start(){
        dragonGame.init();
    }

    public void finish(){
        dragonGame.finish();
    }

    public void setPlayMode(){
        int current = dragonStructure.getAltars().stream().filter(DragonAltar::isInUse).collect(Collectors.toSet()).size();
        int total = dragonStructure.getAltars().size();
        if(current < total) return;
        int toRemove = current - total;
        int count = 0;
        for(DragonAltar dragonAltar : dragonStructure.getAltars()){
            if(count < toRemove){
                dragonAltar.setInUse(false);
                count++;
            }
        }
    }

    public UCEnderDragon getEnderDragon(){
        if(dragonGame.isActive())
            return dragonGame.getEnderDragon();
        return null;
    }

}