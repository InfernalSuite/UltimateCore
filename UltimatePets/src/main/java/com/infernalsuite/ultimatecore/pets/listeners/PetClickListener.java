package com.infernalsuite.ultimatecore.pets.listeners;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.playerdata.User;
import com.infernalsuite.ultimatecore.pets.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PetClickListener implements Listener {

    @EventHandler
    public void onPetInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if(itemInHand.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return;
        if(e.getAction() == Action.PHYSICAL) return;
        NBTItem nbtItem = new NBTItem(itemInHand);
        if(!nbtItem.hasKey("petUUID")) return;
        if(!nbtItem.hasKey("petName")) return;
        String petName = nbtItem.getString("petName");
        int petUUID = nbtItem.getInteger("petUUID");
        player.setItemInHand(null);
        User user = HyperPets.getInstance().getUserManager().getUser(player);
        user.getInventoryPets().add(petUUID);
        player.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("petAdded").replace("%name%", petName)));
        // PrisonPets.getInstance().getPets().managePet(player, petName, petUUID);
        e.setCancelled(true);
    }
}
