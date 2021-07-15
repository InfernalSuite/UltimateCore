package mc.ultimatecore.pets.managers;

import com.cryptomorin.xseries.XSound;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.api.events.PetLevelUPEvent;
import mc.ultimatecore.pets.api.events.PetXPGainEvent;
import mc.ultimatecore.pets.objects.Pet;
import mc.ultimatecore.pets.objects.PetData;
import mc.ultimatecore.pets.objects.PlayerPet;
import mc.ultimatecore.pets.objects.Tier;
import mc.ultimatecore.pets.objects.rewards.PetReward;
import mc.ultimatecore.pets.playerdata.User;
import mc.ultimatecore.pets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

@RequiredArgsConstructor
public class PetsLeveller {

    private final HyperPets plugin;

    public void addXP(Player player, int xp) {
        if (xp != 0) {
            User user = plugin.getUserManager().getUser(player);
            String petID = user.getPlayerPet().getPetData().getPetName();
            if(petID != null) {
                Pet pet = plugin.getPets().getPetByID(petID);
                if(pet == null) return;
                PlayerPet petManager = user.getPlayerPet();
                if(petManager == null) return;
                PetData petData = petManager.getPetData();
                Tier tier = petData.getTier();
                if(tier == null) return;
                manageXP(player, pet, xp);
            }
        }
    }

    public void manageXP(Player player, Pet pet, double toAddXP) {
        User user = plugin.getUserManager().getUser(player);
        PlayerPet petManager = user.getPlayerPet();
        PetData petData = user.getPlayerPet().getPetData();
        int level = petData.getLevel();
        Tier tier = petData.getTier();
        Optional<Tier> nextTier = plugin.getTiers().getNextTier(tier.getTierValue());
        int maxLevel = pet.getMaxLevel(tier.getName());
        //Checking if it's the limit
        if(level == maxLevel && !nextTier.isPresent()) return;
        //Adding New XP
        petData.addXP(toAddXP);
        Bukkit.getServer().getPluginManager().callEvent(new PetXPGainEvent(player, pet, (int) toAddXP));
        double xp = petData.getXp();
        double maxXP = pet.getLevelRequirement(tier.getName(), level);
        if(xp >= maxXP){
            if(level == maxLevel){
                petData.setTier(nextTier.get());
                petData.setLevel(1);
                petData.setXp(0);
                plugin.getMessages().getTierLevelUPMessage().forEach(message -> player.sendMessage(Utils.color(message.replace("%tier%", nextTier.get().getDisplayName()))));
            }else {
                int newLevel = level+1;
                petData.setLevel(newLevel);
                petData.setXp(0);
                Bukkit.getServer().getPluginManager().callEvent(new PetLevelUPEvent(player, pet, newLevel));
                player.sendMessage(Utils.color(plugin.getMessages().getMessage("levelUPMessage").replace("%level%", String.valueOf(newLevel)).replace("%name%", Utils.color(pet.getDisplayName()))));
            }
            playSound(player, plugin.getConfiguration().petLevelUPSound);
            PetReward reward = pet.getPetReward(tier.getName(), level+1);
            if(reward != null) reward.execute(player);
            petManager.reloadPet();
        }
    }


    private static void playSound(Player player, String sound) {
        try {
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
        } catch (Exception e) {
        }
    }

}
