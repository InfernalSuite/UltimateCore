package mc.ultimatecore.skills.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.armorequipevent.ArmorEquipEvent;
import mc.ultimatecore.skills.utils.ItemStatsUtils;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ArmorEquipListener implements Listener {
    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {
        if (e.isCancelled()) return;
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        NBTItem armor;
        if (e.getNewArmorPiece() != null && e.getNewArmorPiece().getType() != Material.AIR) {
            armor = new NBTItem(e.getNewArmorPiece());
            if (Utils.hasEffectInHand(armor.getItem())) return;
            ItemStatsUtils.getItemAbilities(armor, false).forEach((ability, value) -> HyperSkills.getInstance().getApi().addArmorAbility(uuid, ability, value));
            ItemStatsUtils.getItemPerks(armor, false).forEach((perk, value) -> HyperSkills.getInstance().getApi().addArmorPerk(uuid, perk, value));
        }
        if (e.getOldArmorPiece() != null && e.getOldArmorPiece().getType() != Material.AIR) {
            armor = new NBTItem(e.getOldArmorPiece());
            if (Utils.hasEffectInHand(armor.getItem())) return;
            ItemStatsUtils.getItemAbilities(armor, false).forEach((ability, value) -> HyperSkills.getInstance().getApi().removeArmorAbility(uuid, ability, value));
            ItemStatsUtils.getItemPerks(armor, false).forEach((perk, value) -> HyperSkills.getInstance().getApi().removeArmorPerk(uuid, perk, value));
        }
    }
}
