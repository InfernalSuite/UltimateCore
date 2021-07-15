package mc.ultimatecore.runes.runetable;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.runes.Item;
import mc.ultimatecore.runes.utils.InventoryUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.Collections;
import java.util.Objects;

public class RuneTableStructure {

    public static void place(Location location){
        Location wallLocation = location.clone().add(0,1,0);
        wallLocation.getBlock().setType(Objects.requireNonNull(XMaterial.COBBLESTONE_WALL.parseMaterial()));
        setPart(getNewLocation(location.clone(), 0.20, 0.5, 0.20, false), false, XMaterial.STONE_SLAB.parseItem());
        setPart(getNewLocation(location.clone(), 0.8, 0.5, 0.2, false), false, XMaterial.STONE_SLAB.parseItem());
        setPart(getNewLocation(location.clone(), 0.2, 0.5, 0.8, false), false, XMaterial.STONE_SLAB.parseItem());
        setPart(getNewLocation(location.clone(), 0.8, 0.5, 0.8, false), false, XMaterial.STONE_SLAB.parseItem());
        setPart(getNewLocation(location.clone(), 0.499, -0.5, 0.489, false), false, XMaterial.STONE.parseItem());
        setPart(getNewLocation(location.clone(), 0.45, 1.3, 0.7, true), true, XMaterial.PLAYER_HEAD.parseItem());
    }


    private static void setPart(Location location, boolean head, ItemStack itemStack) {
        ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setCanPickupItems(false);
        stand.setCustomName("RuneTable");
        if(head) {
            Item item = new Item(XMaterial.PLAYER_HEAD, 4,
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM1NDAyOThhMDE3YjI1ZjljZmFlOTI4MWZlNWI1ODVkNzcwZGIxODUyYjczODA0ZDFiYjdjN2VlNTM3MzNhNCJ9fX0=", 4, "", Collections.singletonList(""));
            stand.setHelmet(InventoryUtils.makeItem(item));
            EulerAngle newRot = stand.getHeadPose().add(-89.52f, 0, 0);
            stand.setHeadPose(newRot);
            stand.setSmall(true);
        }else{
            stand.setHelmet(itemStack);
        }
    }

    private static Location getNewLocation(Location location, Double x, Double y, Double z, boolean yaw){
        if(yaw) location.setYaw(25);
        return location.add(x, y, z);
    }

}
