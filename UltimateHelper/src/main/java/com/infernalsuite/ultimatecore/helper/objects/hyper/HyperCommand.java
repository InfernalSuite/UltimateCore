package com.infernalsuite.ultimatecore.helper.objects.hyper;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
public abstract class HyperCommand {
    
    public final
    List<String> aliases;
    public final String description;
    public final String permission;
    public final boolean onlyForPlayers;
    public final boolean enabled = true;
    public final String usage;
    
    public HyperCommand(List<String> aliases, String description, String permission, boolean onlyForPlayers, String usage) {
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
        this.onlyForPlayers = onlyForPlayers;
        this.usage = usage;
    }
    
    public abstract void execute(CommandSender sender, String[] arguments);
    
    public abstract List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args);
}
