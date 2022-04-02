package mc.ultimatecore.crafting.nms;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class v1_18_R2 implements NMS {
    @Override
    public InventoryView getInventoryView(Player p, String name, org.bukkit.World world, Location location, FixedMetadataValue metadataValue) {
        net.minecraft.server.MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        net.minecraft.server.level.EntityPlayer entityPlayer = new net.minecraft.server.level.EntityPlayer(nmsServer, nmsWorld, profile);
        entityPlayer.getBukkitEntity().setMetadata("NPC", metadataValue);
        entityPlayer.b = new PlayerConnection(nmsServer, new NetworkManager(net.minecraft.network.protocol.EnumProtocolDirection.b), entityPlayer);
        entityPlayer.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        nmsWorld.b(entityPlayer);
        net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo playerInfoAdd = new net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo(net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entityPlayer);
        net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn namedEntitySpawn = new net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn(entityPlayer);
        net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation headRotation = new net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation(entityPlayer, (byte) (int) (location.getYaw() * 256.0F / 360.0F));
        net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo playerInfoRemove = new net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo(net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.EnumPlayerInfoAction.b, entityPlayer);
        net.minecraft.server.network.PlayerConnection connection = (((CraftPlayer) p).getHandle()).b;
        connection.a(playerInfoAdd);
        connection.a(namedEntitySpawn);
        connection.a(headRotation);
        connection.a(playerInfoRemove);
        p.hidePlayer(entityPlayer.getBukkitEntity());
        return entityPlayer.getBukkitEntity().openWorkbench(entityPlayer.getBukkitEntity().getLocation(), true);
    }
}