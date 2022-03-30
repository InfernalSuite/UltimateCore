package com.infernalsuite.ultimatecore.dragon.objects.others;

import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.HyperDragon;
import com.infernalsuite.ultimatecore.dragon.objects.MythicDragon;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IHyperDragon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class RandomDragon {
    private final boolean useRandom;
    private final String id;
    private final boolean isFromMythicMobs;
    private final int level;

    public IHyperDragon getDragon(){
        if(useRandom) {
            return getRandomDragon();
        }else{
            if(isFromMythicMobs)
                return new MythicDragon(id, 100, 100, level);
            else
                return HyperDragons.getInstance().getDragons().getDragonMap().get(id);
        }
    }

    private HyperDragon getRandomDragon(){
        List<IHyperDragon> dragonList = new ArrayList<>(HyperDragons.getInstance().getDragons().getDragonMap().values());
        dragonList.sort(Comparator.comparingDouble(IHyperDragon::getChance));
        int random = new Random().nextInt(100);
        HyperDragon randomDragon = null;
        for(IHyperDragon dragon : dragonList){
            if(random < dragon.getChance()){
                randomDragon = (HyperDragon) dragon;
                break;
            }
        }
        if(randomDragon == null)
            randomDragon = (HyperDragon) dragonList.get(0);
        return randomDragon;
    }
}
