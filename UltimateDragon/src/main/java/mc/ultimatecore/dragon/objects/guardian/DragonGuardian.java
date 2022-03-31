package mc.ultimatecore.dragon.objects.guardian;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.dragon.objects.implementations.IGuardian;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

@Getter
@Setter
@AllArgsConstructor
public class DragonGuardian implements IGuardian {
    private final String id;
    private String displayName;
    private double health;
    private double damage;
    private final GuardianArmor guardianArmor;
    private String entity;

    public DragonGuardian(String id, GuardianArmor guardianArmor) {
        this.id = id;
        this.guardianArmor = guardianArmor;
    }

    @Override
    public Entity spawn(Location location) {
        if(location == null || health <= 1 || entity == null) return null;
        LivingEntity ent = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.valueOf(entity));
        if(displayName != null){
            ent.setCustomName(StringUtils.color(displayName));
            ent.setCustomNameVisible(true);
        }
        ent.setMaxHealth(health);
        ent.setHealth(health);
        EntityEquipment equip = ent.getEquipment();
        if (equip != null) {
            if(guardianArmor.getHelmet() != null) equip.setHelmet(guardianArmor.getHelmet());
            if(guardianArmor.getChestplate() != null) equip.setChestplate(guardianArmor.getChestplate());
            if(guardianArmor.getLeggings() != null) equip.setLeggings(guardianArmor.getLeggings());
            if(guardianArmor.getBoots() != null) equip.setBoots(guardianArmor.getBoots());
        }
        return ent;
    }

    @Override
    public String getID() {
        return id;
    }
}
