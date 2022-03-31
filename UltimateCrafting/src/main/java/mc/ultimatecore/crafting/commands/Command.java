package mc.ultimatecore.crafting.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Command {
    @NotNull
    private final List<String> aliases;
    @NotNull
    private final String description;
    @NotNull
    private final String permission;

    private final boolean player;

    private final boolean enabled = true;


    public Command(@NotNull List<String> aliases, @NotNull String description, @NotNull String permission, boolean player) {
        super();
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
        this.player = player;
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public abstract void admin(CommandSender sender, String[] args);

    public abstract List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args);

    public List<String> getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isPlayer() {
        return player;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void playSound() {

    }
}