package mc.ultimatecore.pets.gui;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.Pet;
import mc.ultimatecore.pets.objects.PetData;
import mc.ultimatecore.pets.objects.PlayerPet;
import mc.ultimatecore.pets.objects.Tier;
import mc.ultimatecore.pets.playerdata.User;
import mc.ultimatecore.pets.utils.Placeholder;
import mc.ultimatecore.pets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetGUI implements GUI {

    private final User user;
    private final HyperPets plugin = HyperPets.getInstance();
    private final Map<Integer, PetData> petDataMap;
    private boolean itemMode;
    @Getter
    private boolean hideMode;

    public PetGUI(User user, boolean itemMode, boolean hideMode) {
        this.user = user;
        this.petDataMap = new HashMap<>();
        this.itemMode = itemMode;
        this.hideMode = hideMode;
    }

    public PetGUI(User user) {
        this.user = user;
        this.petDataMap = new HashMap<>();
        this.itemMode = false;
        this.hideMode = false;
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, plugin.getInventories().petsGUISize, Utils.color(plugin.getInventories().petsGUITitle));
        for (int i = 0; i < inventory.getSize(); i++)
            if (!plugin.getInventories().petsGUIExcludedSlots.contains(i))
                inventory.setItem(i, Utils.makeItem(plugin.getInventories().background));
        int count = 0;
        PetData playerPet = plugin.getApi().getPetData(user.uuid);
        String currentPet = playerPet == null ? plugin.getMessages().getMessage("noPet") : plugin.getPets().getPetByID(playerPet.getPetName()).getDisplayName();
        for (Integer slot : plugin.getInventories().petSlots) {
            List<Integer> petIDs = user.getInventoryPets();
            if (petIDs.size() <= count) {
                inventory.setItem(slot, XMaterial.AIR.parseItem());
                continue;
            }
            PetData petData = plugin.getPetsManager().getPetDataByID(petIDs.get(count));
            if (petData == null)
                continue;

            Pet pet = plugin.getPets().getPetByID(petData.getPetName());
            if (pet == null)
                continue;
            if (user.getPlayerPet() != null && petData.getPetUUID().equals(user.getPlayerPet().getPetData().getPetUUID()))
                inventory.setItem(slot, Utils.makeItemHidden(plugin.getInventories().petItemInGUI, Utils.getPetEquippedPlaceholders(user), pet));
            else
                inventory.setItem(slot, Utils.makeItemHidden(plugin.getInventories().petItemInGUI, Utils.getPetUnequippedPlaceholders(user, petData), pet));
            petDataMap.put(slot, petData);
            count++;
        }

        if (plugin.getInventories().mainMenuBackEnabled)
            inventory.setItem(plugin.getInventories().mainMenuBack.slot, Utils.makeItem(plugin.getInventories().mainMenuBack));

        inventory.setItem(plugin.getInventories().petInfo.slot, Utils.makeItemHidden(plugin.getInventories().petInfo, Collections.singletonList(new Placeholder("selected_pet", currentPet))));

        if (itemMode)
            inventory.setItem(plugin.getInventories().convertItemEnabled.slot, Utils.makeItem(plugin.getInventories().convertItemEnabled));
        else
            inventory.setItem(plugin.getInventories().convertItemDisabled.slot, Utils.makeItem(plugin.getInventories().convertItemDisabled));
        if (!hideMode)
            inventory.setItem(plugin.getInventories().hidePetsDisabled.slot, Utils.makeItemHidden(plugin.getInventories().hidePetsDisabled, Collections.singletonList(new Placeholder("selected_pet", currentPet))));
        else
            inventory.setItem(plugin.getInventories().hidePetsEnabled.slot, Utils.makeItemHidden(plugin.getInventories().hidePetsEnabled, Collections.singletonList(new Placeholder("selected_pet", currentPet))));

        inventory.setItem(plugin.getInventories().closeButton.slot, Utils.makeItem(plugin.getInventories().closeButton));

        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            int slot = e.getSlot();
            if (slot == plugin.getInventories().closeButton.slot) {
                player.closeInventory();
                return;
            } else if (e.getSlot() == plugin.getInventories().mainMenuBack.slot && plugin.getInventories().mainMenuBackEnabled) {
                player.closeInventory();
                String command = plugin.getInventories().mainMenuBack.command;
                if(command.contains("%player%"))
                    Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())), 3);
                else
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.performCommand(command), 3);
                return;
            } else if (slot == plugin.getInventories().convertItemEnabled.slot || slot == plugin.getInventories().convertItemDisabled.slot) {
                itemMode = !itemMode;
            } else if (slot == plugin.getInventories().hidePetsDisabled.slot || slot == plugin.getInventories().hidePetsEnabled.slot) {
                hideMode = !hideMode;
                PlayerPet playerPet = user.getPlayerPet();
                if (playerPet != null) {
                    if (hideMode)
                        playerPet.removeStand();
                    else
                        playerPet.spawnStand();
                }
            } else {
                if (itemMode) {
                    if (petDataMap.containsKey(slot)) {
                        e.setCurrentItem(null);
                        PetData petData = petDataMap.get(slot);
                        String petKey = petData.getPetName();
                        Integer petUUID = petData.getPetUUID();
                        Tier tier = petData.getTier();
                        petDataMap.remove(slot);
                        user.getInventoryPets().remove(petUUID);
                        PlayerPet petManager = user.getPlayerPet();
                        if (petManager != null && petManager.getPetData().getPetName().equals(petKey))
                            if (petManager.getPetData().getPetUUID().equals(petUUID))
                                petManager.removePet(false);
                        player.getInventory().addItem(plugin.getPets().getPetItem(petKey, petUUID, tier));
                    }else{
                        return;
                    }
                } else {
                    if (petDataMap.containsKey(slot)) {
                        PetData petData = petDataMap.get(slot);
                        plugin.getPets().managePet(player, petData.getPetName(), petData.getPetUUID());
                    }else{
                        return;
                    }
                }

            }
            Bukkit.getScheduler().runTaskLater(HyperPets.getInstance(), () -> player.openInventory(new PetGUI(user, itemMode, hideMode).getInventory()), 3);
        }
    }
}
