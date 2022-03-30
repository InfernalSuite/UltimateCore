package com.infernalsuite.ultimatecore.skills.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.TempUser;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import com.infernalsuite.ultimatecore.skills.utils.ItemStatsUtils;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class ItemStatsListener implements Listener {

    private final HyperSkills plugin;

    @EventHandler
    public void onItemSelect(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(e.getNewSlot());
        TempUser user = TempUser.getUser(player);
        UUID uuid = player.getUniqueId();
        if(!user.getTempAbility().isEmpty()){
            user.getTempAbility().forEach((ability, value) -> plugin.getApi().removeArmorAbility(uuid, ability, value));
            user.getTempAbility().clear();
        }
        if(!user.getTempPerk().isEmpty()){
            user.getTempPerk().forEach((perk, value) -> plugin.getApi().removeArmorPerk(uuid, perk, value));
            user.getTempPerk().clear();
        }
        //ABILITIES
        if(!Utils.hasEffectInHand(itemStack)) return;
        Map<Ability, Double> abilities = ItemStatsUtils.getItemAbilities(new NBTItem(itemStack), true);
        abilities.forEach((ability, value) -> plugin.getApi().addArmorAbility(uuid, ability, value));
        user.setTempAbility(abilities);
        //PERKS
        Map<Perk, Double> perks = ItemStatsUtils.getItemPerks(new NBTItem(itemStack), true);
        perks.forEach((perk, value) -> plugin.getApi().addArmorPerk(uuid, perk, value));
        user.setTempPerk(perks);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void playerClickEvent(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) {
            Player player = (Player) e.getDamager();
            ItemStack itemStack = player.getItemInHand();
            if (itemStack == null || itemStack.getType() == Material.AIR) return;
            NBTItem nbtItem = new NBTItem(itemStack);
            Double mmoCost = ItemStatsUtils.getMMOManaCost(nbtItem);
            if(mmoCost > 0) return;
            double manaCost = ItemStatsUtils.getManaCost(nbtItem) + mmoCost;
            if(manaCost < 1) return;
            double currentMana = plugin.getApi().getTotalAbility(player.getUniqueId(), Ability.Intelligence);
            if(manaCost > currentMana){
                e.setCancelled(true);
                return;
            }
            plugin.getApi().removeAbility(player.getUniqueId(), Ability.Intelligence, manaCost);
        }
    }

}
