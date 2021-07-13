package mc.ultimatecore.crafting.nms;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class v1_8_R3 implements NMS {
    
    @Override
    public InventoryView getInventoryView(Player p, String name, org.bukkit.World world, Location location, FixedMetadataValue metadataValue) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        PlayerInteractManager interactManager = new PlayerInteractManager(nmsWorld);
        EntityPlayer entityPlayer = new EntityPlayer(nmsServer, nmsWorld, profile, interactManager);
        entityPlayer.getBukkitEntity().setMetadata("NPC", metadataValue);
        
        entityPlayer.playerConnection = new PlayerConnection(nmsServer, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), entityPlayer);
        entityPlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        nmsWorld.addEntity((Entity) entityPlayer);
        //PacketPlayOutPlayerInfo playerInfoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { entityPlayer });
        PacketPlayOutNamedEntitySpawn namedEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);
        PacketPlayOutEntityHeadRotation headRotation = new PacketPlayOutEntityHeadRotation(entityPlayer, (byte) (int) (location.getYaw() * 256.0F / 360.0F));
        //PacketPlayOutPlayerInfo playerInfoRemove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { entityPlayer });
        PlayerConnection connection = (((CraftPlayer) p).getHandle()).playerConnection;
        //connection.sendPacket((Packet)playerInfoAdd);
        connection.sendPacket(namedEntitySpawn);
        connection.sendPacket(headRotation);
        //connection.sendPacket((Packet)playerInfoRemove);
        p.hidePlayer(entityPlayer.getBukkitEntity());
        return entityPlayer.getBukkitEntity().openWorkbench(entityPlayer.getBukkitEntity().getLocation(), true);
    }
}