package mc.ultimatecore.pets.managers;

import com.cryptomorin.xseries.*;
import lombok.*;
import mc.ultimatecore.pets.*;
import mc.ultimatecore.pets.api.events.*;
import mc.ultimatecore.pets.objects.*;
import mc.ultimatecore.pets.objects.rewards.*;
import mc.ultimatecore.pets.playerdata.*;
import mc.ultimatecore.pets.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

@RequiredArgsConstructor
public class PetsLeveller {

    private final HyperPets plugin;

    public void addXP(Player player, double xp) {
        if (xp == 0.0D) {
            return;
        }

        User user = plugin.getUserManager().getUser(player);
        String petID = user.getPlayerPet().getPetData().getPetName();
        if (petID == null) {
            return;
        }

        Pet pet = plugin.getPets().getPetByID(petID);
        if (pet == null) {
            return;
        }

        PlayerPet petManager = user.getPlayerPet();
        if (petManager == null) {
            return;
        }

        PetData petData = petManager.getPetData();
        Tier tier = petData.getTier();
        if (tier == null) {
            return;
        }
        manageXP(player, pet, xp);
    }

    public void manageXP(Player player, Pet pet, double toAddXP) {
        System.out.println(plugin.getAddonsManager().isHyperSkills());
        User user = plugin.getUserManager().getUser(player);
        PlayerPet petManager = user.getPlayerPet();
        PetData petData = user.getPlayerPet().getPetData();
        int level = petData.getLevel();
        Tier tier = petData.getTier();
        Optional<Tier> nextTier = plugin.getTiers().getNextTier(tier.getTierValue());
        int maxLevel = pet.getMaxLevel(tier.getName());
        //Checking if it's the limit
        if (level == maxLevel && !nextTier.isPresent()) {
            return;
        }
        //Adding New XP
        petData.addXP(toAddXP);
        Bukkit.getServer().getPluginManager().callEvent(new PetXPGainEvent(player, pet, toAddXP));
        double xp = petData.getXp();
        double maxXP = pet.getLevelRequirement(tier.getName(), level);
        if (xp < maxXP) {
            return;
        }

        if (level == maxLevel) {
            petData.setTier(nextTier.get());
            petData.setLevel(1);
            petData.setXp(0);
            plugin.getMessages().getTierLevelUPMessage().forEach(message -> player.sendMessage(Utils.color(message.replace("%tier%", nextTier.get().getDisplayName()))));
        } else {
            int newLevel = level + 1;
            petData.setLevel(newLevel);
            petData.setXp(0);
            Bukkit.getServer().getPluginManager().callEvent(new PetLevelUPEvent(player, pet, newLevel));
            player.sendMessage(Utils.color(plugin.getMessages().getMessage("levelUPMessage").replace("%level%", String.valueOf(newLevel)).replace("%name%", Utils.color(pet.getDisplayName()))));
        }

        playSound(player, plugin.getConfiguration().petLevelUPSound);
        PetReward reward = pet.getPetReward(tier.getName(), level + 1);
        if (reward != null) {
            reward.execute(player);
        }
        petManager.reloadPet();
    }

    private static void playSound(Player player, String sound) {
        player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
    }
}
