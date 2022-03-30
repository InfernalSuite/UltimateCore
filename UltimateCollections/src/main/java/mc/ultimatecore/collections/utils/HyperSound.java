package mc.ultimatecore.collections.utils;

import com.cryptomorin.xseries.XSound;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class HyperSound {
    
    private String sound;
    private float volume;
    private float pitch;
    
    public void play(Player player) {
        try {
            player.playSound(player.getLocation(), Objects.requireNonNull(XSound.valueOf(sound).parseSound()), volume, pitch);
        } catch (Exception ignored) {
            Bukkit.getLogger().warning("Invalid Sound " + sound + "!");
        }
    }
}
