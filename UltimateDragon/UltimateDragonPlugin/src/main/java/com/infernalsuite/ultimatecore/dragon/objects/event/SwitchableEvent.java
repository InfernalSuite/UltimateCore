package com.infernalsuite.ultimatecore.dragon.objects.event;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IDragonEvent;

import java.util.Map;

@RequiredArgsConstructor
public class SwitchableEvent {
    private final Map<Integer, IDragonEvent> events;
    private IDragonEvent currentEvent;

    public IDragonEvent getNext(){
        if(currentEvent == null){
            currentEvent = events.getOrDefault(0, null);
        }else{
            Integer next = getCurrentPosEvent();
            if(next != null){
                next++;
                currentEvent = events.containsKey(next) ? events.get(next) : events.getOrDefault(0, null);
            }
        }
        return currentEvent;
    }

    public IDragonEvent getCurrentEvent(){
        return currentEvent;
    }

    private Integer getCurrentPosEvent(){
        for(Integer integer : events.keySet())
            if(events.get(integer) == currentEvent) return integer;
        return null;
    }
}
