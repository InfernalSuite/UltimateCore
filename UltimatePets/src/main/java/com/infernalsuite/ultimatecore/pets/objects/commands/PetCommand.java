package com.infernalsuite.ultimatecore.pets.objects.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class PetCommand {
    private final String command;
    private final PetCommandType petCommandType;
    private final int seconds;

    public void execute(Player player){
        if(player == null) return;
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
    }
}
