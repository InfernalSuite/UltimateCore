package mc.ultimatecore.pets.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.pets.armorequipevent.ArmorEquipEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetEquipListener implements Listener {

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {
        if(e.isCancelled()) return;
        NBTItem armor;
        if (e.getNewArmorPiece() != null && e.getNewArmorPiece().getType() != Material.AIR) {
            armor = new NBTItem(e.getNewArmorPiece());
            if(!armor.hasKey("petUUID")) return;
            if(!armor.hasKey("petName")) return;
            e.setCancelled(true);
        }
    }
}
