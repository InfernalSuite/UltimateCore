package mc.ultimatecore.alchemy.commands;

import com.cryptomorin.xseries.XSound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

    private final String sound;

    private final String wrongSong;

    public Command(@NotNull List<String> aliases, @NotNull String description, @NotNull String permission, boolean player, String sound, String wrongSong) {
        super();
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
        this.player = player;
        this.sound = sound;
        this.wrongSong = wrongSong;
    }

    public Command(@NotNull List<String> aliases, @NotNull String description, @NotNull String permission, boolean player) {
        super();
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
        this.player = player;
        this.sound = null;
        this.wrongSong = null;
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public abstract void admin(CommandSender sender, String[] args);

    public abstract List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args);

    public String getSound() {
        return sound;
    }

    public String getWrongSound() {
        return wrongSong;
    }

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

    public void playSound(Player player) {
        if (sound != null)
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
    }

    public void playWrongSong(Player player) {
        if (wrongSong != null)
            player.playSound(player.getLocation(), XSound.valueOf(wrongSong).parseSound(), 1, 1);
    }
}