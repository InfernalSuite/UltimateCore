package mc.ultimatecore.pets.objects.implementations;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.Item;
import mc.ultimatecore.pets.api.events.PetDespawnEvent;
import mc.ultimatecore.pets.api.events.PetSpawnEvent;
import mc.ultimatecore.pets.objects.EquipPetType;
import mc.ultimatecore.pets.objects.Pet;
import mc.ultimatecore.pets.objects.PetData;
import mc.ultimatecore.pets.objects.PlayerPet;
import mc.ultimatecore.pets.playerdata.User;
import mc.ultimatecore.pets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.UUID;

public class PlayerPetLegacy extends PlayerPet implements Listener {
    
    private Location sl;
    
    private Location nl;
    
    public PlayerPetLegacy(UUID petOwner, int petUUID) {
        super(petOwner, petUUID);
        this.count = 0;
        HyperPets.getInstance().registerListeners(this);
    }
    
    @Override
    public void spawnStand() {
        User user = HyperPets.getInstance().getUserManager().getUser(petOwner);
        if (user == null) return;
        if (user.isHidePets()) return;
        Pet pet = getPet();
        ItemStack head = Utils.makeItem(new Item(XMaterial.PLAYER_HEAD, 1, pet.getTexture(), 1, "", Collections.singletonList("")));
        if (head == null) return;
        createPetStand(head, pet);
    }
    
    @Override
    public void removeStand() {
        if (nameEntity != null)
            nameEntity.remove();
        if (petEntity != null)
            petEntity.remove();
    }
    
    public void reloadPet() {
        Pet pet = getPet();
        if (pet == null) return;
        Player player = getPlayer();
        pet.removeStats(player, petData.getTier().getName(), petData.getLevel() - 1);
        pet.applyNewStats(player, petData.getTier().getName(), petData.getLevel());
        if (nameEntity != null)
            nameEntity.setCustomName(Utils.color(pet.getEntityName()
                                                    .replaceAll("%pet_name%", pet.getDisplayName())
                                                    .replaceAll("%player%", getPlayer().getName())
                                                    .replaceAll("%pet_level%", String.valueOf(petData.getLevel()))
                                                    .replaceAll("%pet_tier%", petData.getTier().getDisplayName())
            ));
    }
    
    public void createPet() {
        Player player = getPlayer();
        User user = HyperPets.getInstance().getUserManager().getUser(player);
        Bukkit.getServer().getPluginManager().callEvent(new PetSpawnEvent(player, getPet(), petData));
        user.spawnedID = petData.getPetUUID();
        Pet pet = getPet();
        spawnStand();
        if (pet == null) return;
        
        pet.applyNewStats(player, petData.getTier().getName(), petData.getLevel());
        
        pet.getPetCommands().apply(player, EquipPetType.EQUIP);
        initPet();
    }
    
    public void removePet(boolean serverClose) {
        removeStand();
        Player player = getPlayer();
        User user = HyperPets.getInstance().getUserManager().getUser(player);
        if (taskID != null) Bukkit.getScheduler().cancelTask(taskID);
        user.removePetManager();
        if (!serverClose) user.spawnedID = -1;
        Pet pet = getPet();
        
        pet.removeStats(player, petData.getTier().getName(), petData.getLevel());
        
        pet.getPetCommands().apply(player, EquipPetType.UNEQUIP);
        Bukkit.getServer().getPluginManager().callEvent(new PetDespawnEvent(player, getPet(), petData));
    }
    
    private void initPet() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        Player player = getPlayer();
        this.taskID = (new BukkitRunnable() {
            public void run() {
                if (!movePet(player))
                    if (taskID != null)
                        scheduler.cancelTask(taskID);
            }
        }).runTaskTimer(HyperPets.getInstance(), 2L, 2L).getTaskId();
        
    }
    
    
    private boolean movePet(Player player) {
        if (petEntity != null && nameEntity != null) {
            if (player == null || !player.isOnline()) return false;
            Vector currentLocation = player.getLocation().toVector();
            if (last != null && last.equals(currentLocation)) {
                if (HyperPets.getInstance().getConfiguration().upAndDownPets)
                    tpAFK();
                return true;
            }
            count = 0;
            last = player.getLocation().toVector();
            Location to = Utils.getDirection(player.getLocation().clone(), HyperPets.getInstance().getNms().addStand());
            Location nt = Utils.getDirection(player.getLocation().clone(), HyperPets.getInstance().getNms().addName());
            if (HyperPets.getInstance().getNms().inverted())
                to.setDirection(to.getDirection().multiply(-1));
            petEntity.teleport(to.add(HyperPets.getInstance().getNms().vectorStand()));
            nameEntity.teleport(nt.clone().subtract(HyperPets.getInstance().getNms().vectorName()));
            sl = petEntity.getLocation();
            nl = nameEntity.getLocation();
            return true;
        }
        return true;
    }
    
    private void tpAFK() {
        if (this.count <= 20) {
            Location ssl = this.sl.clone().add(0.0D, this.count * 0.03D, 0.0D);
            Location nsl = this.nl.clone().add(0.0D, this.count * 0.03D, 0.0D);
            this.petEntity.teleport(ssl);
            this.nameEntity.teleport(nsl);
        } else {
            if (this.count == 40)
                this.count = 0;
            Location ssl = this.sl.clone().subtract(0.0D, this.count * 0.03D, 0.0D);
            Location nsl = this.nl.clone().subtract(0.0D, this.count * 0.03D, 0.0D);
            this.petEntity.teleport(ssl);
            this.nameEntity.teleport(nsl);
        }
        this.count++;
    }
    
    private void createPetStand(ItemStack head, Pet pet) {
        Player player = Bukkit.getPlayer(petOwner);
        if (player == null) return;
        Location playerLocation = player.getLocation();
        Location to = Utils.getDirection(playerLocation.clone(), HyperPets.getInstance().getNms().addStand());
        if (HyperPets.getInstance().getNms().inverted())
            to.setDirection(to.getDirection().multiply(-1));
        Location nt = Utils.getDirection(playerLocation.clone(), HyperPets.getInstance().getNms().addName());
        nameEntity = playerLocation.getWorld().spawn(nt.clone().subtract(0.0D, 1.1D, 0.0D), ArmorStand.class);
        nameEntity.setVisible(false);
        nameEntity.setGravity(false);
        nameEntity.setCustomNameVisible(true);
        if (XMaterial.getVersion() > 8)
            nameEntity.setCollidable(true);
        nameEntity.setCustomName(Utils.color(pet.getEntityName()
                                                .replaceAll("%pet_name%", pet.getDisplayName())
                                                .replaceAll("%player%", getPlayer().getName())
                                                .replaceAll("%pet_level%", String.valueOf(petData.getLevel()))
                                                .replaceAll("%pet_tier%", petData.getTier().getDisplayName())
        ));
        
        petEntity = player.getWorld().spawn(to, ArmorStand.class);
        petEntity.setVisible(false);
        petEntity.setGravity(false);
        if (XMaterial.getVersion() > 8)
            petEntity.setCollidable(true);
        petEntity.setCustomNameVisible(false);
        petEntity.setRightArmPose(HyperPets.getInstance().getNms().rightArm());
        petEntity.setItemInHand(head);
    }
    
    public PetData getPetData() {
        return petData;
    }
    
    public Pet getPet() {
        return HyperPets.getInstance().getPets().getPetByID(petData.getPetName());
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(petOwner);
    }
    
    @EventHandler
    public void onInteract(PlayerArmorStandManipulateEvent e) {
        if (petEntity != null && e.getRightClicked().equals(petEntity))
            e.setCancelled(true);
        if (nameEntity != null && e.getRightClicked().equals(nameEntity))
            e.setCancelled(true);
    }
    
    @EventHandler
    public void onBurn(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            if (petEntity != null && e.getEntity().equals(petEntity))
                e.getEntity().setFireTicks(0);
            if (nameEntity != null && e.getEntity().equals(nameEntity))
                e.getEntity().setFireTicks(0);
        }
    }
    
}
