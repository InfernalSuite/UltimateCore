package mc.ultimatecore.pets.listeners;

import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.api.events.PetRegisterEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetRegisterListener implements Listener {

    @EventHandler
    public void onRegister(PetRegisterEvent event) {
        try {
            HyperPets.getInstance().getPetsManager().loadPetData(event.getPetData());
        } catch (Exception e) {
            HyperPets.getInstance().sendErrorMessage(e);
        }
    }

}
