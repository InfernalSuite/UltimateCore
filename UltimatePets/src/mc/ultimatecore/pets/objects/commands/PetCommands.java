package mc.ultimatecore.pets.objects.commands;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.EquipPetType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class PetCommands {
    private final Set<BukkitTask> runnable = new HashSet<>();
    private final Set<PetCommand> petCommands;

    public void apply(Player player, EquipPetType equipPetType){
        if(equipPetType == EquipPetType.EQUIP){
            for(PetCommand petCommand : petCommands){
                if(petCommand.getPetCommandType() == PetCommandType.EQUIP)
                    petCommand.execute(player);
                else if(petCommand.getPetCommandType() == PetCommandType.TIMER)
                    runnable.add(getNewTimer(player, petCommand));
            }
        }else{
            petCommands.stream()
                    .filter(petCommand -> petCommand.getPetCommandType() == PetCommandType.UNEQUIP)
                    .forEach(petCommand -> petCommand.execute(player));
            runnable.stream().filter(Objects::nonNull).forEach(BukkitTask::cancel);
        }
    }

    private BukkitTask getNewTimer(Player player, PetCommand petCommand){
        return (new BukkitRunnable() {
            public void run() {
                if(player != null)
                    petCommand.execute(player);
                else
                    cancel();
            }
        }).runTaskTimer(HyperPets.getInstance(), 20L * petCommand.getSeconds(), 20L * petCommand.getSeconds());
    }
}
