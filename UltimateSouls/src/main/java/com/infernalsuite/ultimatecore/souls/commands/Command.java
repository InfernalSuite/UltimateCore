package com.infernalsuite.ultimatecore.souls.commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public abstract class Command {
    
    public final @NotNull
    List<String> aliases;
    public final @NotNull String description;
    public final @NotNull String permission;
    public final boolean onlyForPlayers;
    public final boolean enabled = true;
    public final String usage;
    
    public Command(@NotNull List<String> aliases, @NotNull String description, @NotNull String permission, boolean onlyForPlayers, String usage) {
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
        this.onlyForPlayers = onlyForPlayers;
        this.usage = usage;
    }
    
    public abstract void execute(CommandSender sender, String[] arguments);
    
    public abstract List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args);
    
}