package com.infernalsuite.ultimatecore.talismans.objects.implementations;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.objects.Talisman;
import com.infernalsuite.ultimatecore.talismans.objects.TalismanType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TimerTalisman extends Talisman {
    @Getter
    private final int seconds;

    private final String command;

    public TimerTalisman(String name, TalismanType talismanType, String displayName, List<String> lore, String texture, int seconds, String command) {
        super(name, talismanType, displayName, lore, texture, false);
        this.seconds = seconds;
        this.command = command;
    }

    @Override
    public void execute(Player player, Integer repeat) {
        for(int i = 0; i<repeat; i++)
            Bukkit.getServer().getScheduler().runTask(HyperTalismans.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName())));
    }
}
