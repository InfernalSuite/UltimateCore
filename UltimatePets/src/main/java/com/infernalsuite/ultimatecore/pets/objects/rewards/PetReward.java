package com.infernalsuite.ultimatecore.pets.objects.rewards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@AllArgsConstructor
public class PetReward {
    private final List<String> commands;

    public void execute(Player player){
        commands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
    }
}
