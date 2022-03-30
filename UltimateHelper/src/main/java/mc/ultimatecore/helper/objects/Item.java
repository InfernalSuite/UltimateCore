package mc.ultimatecore.helper.objects;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mc.ultimatecore.helper.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Item implements Cloneable {
    
    public XMaterial material;
    public int amount;
    public String title;
    public String headData;
    public String headOwner;
    public List<String> lore;
    public int slot;
    public boolean isGlowing;
    public String command;
    public String sound;
    
    public Item(XMaterial material, int amount, String title, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
    }
    
    public Item(XMaterial material, int slot, int amount, String title, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
    }
    
    public Item(XMaterial material, int slot, String headData, int amount, String title, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
        this.headData = headData;
    }
    
    public Item(XMaterial material, int slot, int amount, String title, String headOwner, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.headOwner = headOwner;
        this.slot = slot;
    }
    
    public Item(XMaterial material, int amount, String title, String headOwner, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.headOwner = headOwner;
    }
    
    public void execute(Player player) {
        if (command != null) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
        if (sound != null) SoundUtils.playSound(player, sound);
    }
    
}
