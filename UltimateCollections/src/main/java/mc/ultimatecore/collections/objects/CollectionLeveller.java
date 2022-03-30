package mc.ultimatecore.collections.objects;

import com.cryptomorin.xseries.XSound;
import org.bukkit.entity.Player;

public class CollectionLeveller {
    
    
    private static void playSound(Player player, String sound) {
        try {
            if (sound != "")
                player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
        } catch (Exception e) {
        
        }
    }
    
}
