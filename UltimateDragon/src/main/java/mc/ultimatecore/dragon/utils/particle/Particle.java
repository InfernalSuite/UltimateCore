package mc.ultimatecore.dragon.utils.particle;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Particle {
    private static final int versionId = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].replace(".", "#").split("#")[1]);
    private static Class<?> enumParticle;
    private static Class<?> packet;
    private static Method getHandle;
    private static Constructor<?> constructWorldParticles;
    private static boolean invoked;

    public Particle() {
        if (!invoked) {
            try {
                if (versionId < 13) {
                    Class<?> craftPlayer = Reflection.getCraftClass("entity.CraftPlayer");
                    enumParticle = Reflection.getNMSClass("EnumParticle");
                    Class<?> worldParticlesPacket = Reflection.getNMSClass("PacketPlayOutWorldParticles");
                    packet = Reflection.getNMSClass("Packet");
                    getHandle = craftPlayer.getDeclaredMethod("getHandle");
                    constructWorldParticles = worldParticlesPacket.getConstructor(enumParticle, Boolean.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE, int[].class);
                }
            } catch (Exception var2) {
                var2.printStackTrace();
            }

            invoked = true;
        }

    }

    public void sendParticle(Player player, EnumParticle particle, boolean longDistance, World world, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, int... data) {
        this.sendParticle(Collections.singletonList(player), particle, longDistance, world, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data);
    }

    public void sendParticle(Collection<? extends Player> playerList, EnumParticle particle, boolean longDistance, World world, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, int... data) {
        List<Player> list = new ArrayList<>(playerList);
        this.sendParticle(list, particle, longDistance, world, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data);
    }

    public void sendParticle(List<Player> playerList, EnumParticle particle, boolean longDistance, World world, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, int... data) {
        if (versionId >= 13) {
            for (Player player : playerList) {
                if (player.getWorld().getName().equals(world.getName())) {
                    player.spawnParticle(org.bukkit.Particle.valueOf(particle.name()), x, y, z, particleCount, offsetX, offsetY, offsetZ, particleData);
                }
            }
        } else {
            try {
                Object particleObject = Reflection.getField(enumParticle.getDeclaredField(particle.name())).get(null);
                if (particleObject != null) {
                    Object particlePacketObject = constructWorldParticles.newInstance(particleObject, longDistance, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data);

                    for (Player player : playerList) {
                        if (player.getWorld().getName().equals(world.getName())) {
                            Object handle = getHandle.invoke(player);
                            Object connection = Reflection.getValue(handle, "playerConnection");
                            Method send = Reflection.getMethod(connection, "sendPacket", packet);
                            send.invoke(connection, particlePacketObject);
                        }
                    }
                }
            } catch (Exception var21) {
                var21.printStackTrace();
            }
        }

    }
}