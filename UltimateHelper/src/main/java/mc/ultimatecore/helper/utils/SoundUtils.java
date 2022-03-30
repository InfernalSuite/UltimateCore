package mc.ultimatecore.helper.utils;

import com.cryptomorin.xseries.XSound;
import mc.ultimatecore.helper.objects.hyper.HyperSound;
import org.bukkit.entity.Player;

public class SoundUtils {
    
    public static void playSound(Player player, String sound) {
        if (sound == null || sound.equals("")) return;
        try {
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
        } catch (NullPointerException ignored) {
        }
    }
    
    public static void playSound(Player player, HyperSound hyperSound) {
        if (hyperSound == null) return;
        String sound = hyperSound.getSound();
        if (sound == null || sound.equals("")) return;
        try {
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), hyperSound.getVolume(), hyperSound.getPitch());
        } catch (NullPointerException ignored) {
        }
    }
}
