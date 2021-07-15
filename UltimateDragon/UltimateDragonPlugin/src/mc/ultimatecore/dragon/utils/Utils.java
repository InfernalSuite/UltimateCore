package mc.ultimatecore.dragon.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.configs.Messages;
import mc.ultimatecore.dragon.objects.event.DragonBallEvent;
import mc.ultimatecore.dragon.objects.event.FireBallEvent;
import mc.ultimatecore.dragon.objects.event.GuardianEvent;
import mc.ultimatecore.dragon.objects.event.LightningEvent;
import mc.ultimatecore.dragon.objects.guardian.MythicGuardian;
import mc.ultimatecore.dragon.objects.guardian.RandomGuardian;
import mc.ultimatecore.dragon.objects.implementations.IDragonEvent;
import mc.ultimatecore.dragon.objects.implementations.IGuardian;
import mc.ultimatecore.dragon.objects.others.BlockList;
import mc.ultimatecore.dragon.objects.structures.DragonAltar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Utils {
    public static String getStartMessage(IDragonEvent dragonEvent){
        Messages messages = HyperDragons.getInstance().getMessages();
        if(!messages.isEnabled()) return "";
        if(dragonEvent instanceof DragonBallEvent)
            return messages.getMessage("DragonBall");
        else if(dragonEvent instanceof FireBallEvent)
            return messages.getMessage("Fireball");
        else if(dragonEvent instanceof GuardianEvent)
            return messages.getMessage("Guardians");
        else if(dragonEvent instanceof LightningEvent)
            return messages.getMessage("Lightning");
        else
            return "";
    }

    public static int getRandom(int min, int max){
        Random r = new Random();
        return r.nextInt(max-min) + min;
    }

    public static CompletableFuture<BlockList> getCuboid(final Location spawn, HyperDragons plugin) {
        World w = spawn.getWorld();
        Location cornerOne = new Location(w, spawn.getX() - plugin.getConfiguration().sizeX, spawn.getY() - plugin.getConfiguration().sizeY, spawn.getZ() - plugin.getConfiguration().sizeZ);
        Location cornerTwo = new Location(w, spawn.getX() + plugin.getConfiguration().sizeX, spawn.getY() + plugin.getConfiguration().sizeY, spawn.getZ() + plugin.getConfiguration().sizeZ);
        CompletableFuture<BlockList> future = new CompletableFuture<>();
        List<Block> blocks = new ArrayList<>();
        int topBlockX = Math.max(cornerOne.getBlockX(), cornerTwo.getBlockX());
        int topBlockY = Math.max(cornerOne.getBlockY(), cornerTwo.getBlockY());
        int topBlockZ = Math.max(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
        int bottomBlockX = Math.min(cornerOne.getBlockX(), cornerTwo.getBlockX());
        int bottomBlockY = Math.min(cornerOne.getBlockY(), cornerTwo.getBlockY());
        int bottomBlockZ = Math.min(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
        Bukkit.getScheduler().runTaskAsynchronously(HyperDragons.getInstance(), () -> {
            for (int x = bottomBlockX; x <= topBlockX; x++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                        Block b = cornerOne.getWorld().getBlockAt(x, y, z);
                        if (!b.getType().equals(Material.AIR)){
                            blocks.add(b);
                        }
                    }
                }
            }
            future.complete(new BlockList(blocks));
        });
        return future;
    }

    public static String getFormattedLocation(Location location){
        if(location == null || location.getWorld() == null) return "None";
        return location.getWorld().getName() +
                ", " +
                location.getBlockX() +
                ", " +
                location.getBlockY() +
                ", " +
                location.getBlockZ();
    }

    public static boolean itemIsKey(ItemStack itemStack){
        if(itemStack == null || itemStack.getType() == Material.AIR) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("hyperdragon_key");
    }

    public static RandomGuardian getRandomGuardian(List<String> guardianSer) {
        Map<IGuardian, Integer> guardians = new HashMap<>();
        for(String key : guardianSer){
            try {
                String[] split = key.split(":");
                if(split.length == 2){
                    String id = split[0];
                    int chance = Integer.parseInt(split[1]);
                    IGuardian iGuardian = HyperDragons.getInstance().getDragonGuardians().getGuardian(id);
                    if(iGuardian == null) continue;
                    guardians.put(iGuardian, chance);
                }else if(split.length == 3 || split.length == 4){
                    if(split[2].equalsIgnoreCase("MYTHIC_MOB")){
                        if(!HyperDragons.getInstance().getAddonsManager().isMythicMobs()) continue;
                        String id = split[0];
                        int chance = Integer.parseInt(split[1]);
                        int level = split.length == 4 ? Integer.parseInt(split[3]) : 1;
                        MythicGuardian mythicGuardian = new MythicGuardian(id, level);
                        guardians.put(mythicGuardian, chance);
                    }
                }
            }catch (Exception ignored){}
        }
        return new RandomGuardian(guardians);
    }

    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material"))
            item.material = XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".headData")) item.headData = yamlConfig.getString(path + ".headData");
        if (yamlConfig.contains(path + ".headOwner")) item.headOwner = yamlConfig.getString(path + ".headOwner");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }

    public static Location getLocationFromConfig(YamlConfiguration config, String path){
        try {
            World world = Bukkit.getServer().getWorld(config.getString(path + ".world"));
            double x = config.getDouble(path+".x");
            double y = config.getDouble(path+".y");
            double z = config.getDouble(path+".z");
            float yaw = Float.parseFloat(Objects.requireNonNull(config.getString(path + ".yaw")));
            float pitch = Float.parseFloat(Objects.requireNonNull(config.getString(path + ".pitch")));
            return new Location(world, x, y, z, yaw, pitch);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    public static DragonAltar getAltarFromConfig(YamlConfiguration config, String path){
        try {
            World world = Bukkit.getServer().getWorld(config.getString(path + ".world"));
            double x = config.getDouble(path+".x");
            double y = config.getDouble(path+".y");
            double z = config.getDouble(path+".z");
            float yaw = Float.parseFloat(Objects.requireNonNull(config.getString(path + ".yaw")));
            float pitch = Float.parseFloat(Objects.requireNonNull(config.getString(path + ".pitch")));
            Location location = new Location(world, x, y, z, yaw, pitch);
            boolean inUse = config.getBoolean(path + ".inUse");
            return new DragonAltar(location, inUse);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void saveLocationToConfig(YamlConfiguration config, String path, Location location){
        try {
            config.set(path+".world", location.getWorld().getName());
            config.set(path+".x", location.getX());
            config.set(path+".y", location.getY());
            config.set(path+".z", location.getZ());
            config.set(path+".yaw", location.getYaw());
            config.set(path+".pitch", location.getPitch());
        }catch (Exception ignored){
        }
    }

    public static void saveLocationToConfig(YamlConfiguration config, String path, DragonAltar dragonAltar){
        try {
            Location location = dragonAltar.getLocation();
            config.set(path+".world", location.getWorld().getName());
            config.set(path+".x", location.getX());
            config.set(path+".y", location.getY());
            config.set(path+".z", location.getZ());
            config.set(path+".yaw", location.getYaw());
            config.set(path+".pitch", location.getPitch());
            config.set(path+".inUse", dragonAltar.isInUse());
        }catch (Exception ignored){
        }
    }
    public static String doubleToStr(int value){
        return String.valueOf(value);
    }


    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static double getHealth(double health, double maxHealth) {
        double perOne = maxHealth / 200.0D;
        return health * perOne;
    }

    public static double getNewHealth(double maxHealth, double health, double damage) {
        if (maxHealth < 200.0D)
            return Math.max(health - damage, 0.0D);
        double perOne = maxHealth / 200.0D;
        double newHealth = health * perOne - damage;
        return Math.min(getMinMax((int)(newHealth / perOne)), 200.0D);
    }

    public static int getMinMax(int division) {
        return Math.max(division, 0);
    }

    public static String doubleToStr(double value){
        return String.valueOf((int) value);
    }

    public static Location getSafeLocation(Location location){
        /*
        TO DO
         */
        return location;
    }

}
