package com.infernalsuite.ultimatecore.collections.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NBTEditor {
    
    private static final Map<String, Class<?>> classCache;
    
    private static final Map<String, Method> methodCache;
    
    private static final Map<Class<?>, Constructor<?>> constructorCache;
    
    private static final Map<Class<?>, Class<?>> NBTClasses;
    
    private static final Map<Class<?>, Field> NBTTagFieldCache;
    
    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    
    private static final MinecraftVersion LOCAL_VERSION = MinecraftVersion.get(VERSION);
    
    private static Field NBTListData;
    
    private static Field NBTCompoundMap;
    
    static {
        classCache = new HashMap<>();
        try {
            classCache.put("NBTBase", Class.forName("net.minecraft.server." + VERSION + ".NBTBase"));
            classCache.put("NBTTagCompound", Class.forName("net.minecraft.server." + VERSION + ".NBTTagCompound"));
            classCache.put("NBTTagList", Class.forName("net.minecraft.server." + VERSION + ".NBTTagList"));
            classCache.put("MojangsonParser", Class.forName("net.minecraft.server." + VERSION + ".MojangsonParser"));
            classCache.put("ItemStack", Class.forName("net.minecraft.server." + VERSION + ".ItemStack"));
            classCache.put("CraftItemStack", Class.forName("org.bukkit.craftbukkit." + VERSION + ".inventory.CraftItemStack"));
            classCache.put("CraftMetaSkull", Class.forName("org.bukkit.craftbukkit." + VERSION + ".inventory.CraftMetaSkull"));
            classCache.put("Entity", Class.forName("net.minecraft.server." + VERSION + ".Entity"));
            classCache.put("CraftEntity", Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftEntity"));
            classCache.put("EntityLiving", Class.forName("net.minecraft.server." + VERSION + ".EntityLiving"));
            classCache.put("CraftWorld", Class.forName("org.bukkit.craftbukkit." + VERSION + ".CraftWorld"));
            classCache.put("CraftBlockState", Class.forName("org.bukkit.craftbukkit." + VERSION + ".block.CraftBlockState"));
            classCache.put("BlockPosition", Class.forName("net.minecraft.server." + VERSION + ".BlockPosition"));
            classCache.put("TileEntity", Class.forName("net.minecraft.server." + VERSION + ".TileEntity"));
            classCache.put("World", Class.forName("net.minecraft.server." + VERSION + ".World"));
            classCache.put("IBlockData", Class.forName("net.minecraft.server." + VERSION + ".IBlockData"));
            classCache.put("TileEntitySkull", Class.forName("net.minecraft.server." + VERSION + ".TileEntitySkull"));
            classCache.put("GameProfile", Class.forName("com.mojang.authlib.GameProfile"));
            classCache.put("Property", Class.forName("com.mojang.authlib.properties.Property"));
            classCache.put("PropertyMap", Class.forName("com.mojang.authlib.properties.PropertyMap"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        NBTClasses = new HashMap<>();
        try {
            NBTClasses.put(Byte.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagByte"));
            NBTClasses.put(Boolean.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagByte"));
            NBTClasses.put(String.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagString"));
            NBTClasses.put(Double.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagDouble"));
            NBTClasses.put(Integer.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagInt"));
            NBTClasses.put(Long.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagLong"));
            NBTClasses.put(Short.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagShort"));
            NBTClasses.put(Float.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagFloat"));
            NBTClasses.put(Class.forName("[B"), Class.forName("net.minecraft.server." + VERSION + ".NBTTagByteArray"));
            NBTClasses.put(Class.forName("[I"), Class.forName("net.minecraft.server." + VERSION + ".NBTTagIntArray"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        methodCache = new HashMap<>();
        try {
            methodCache.put("get", getNMSClass("NBTTagCompound").getMethod("get", new Class[]{String.class}));
            methodCache.put("set", getNMSClass("NBTTagCompound").getMethod("set", new Class[]{String.class, getNMSClass("NBTBase")}));
            methodCache.put("hasKey", getNMSClass("NBTTagCompound").getMethod("hasKey", new Class[]{String.class}));
            methodCache.put("setIndex", getNMSClass("NBTTagList").getMethod("a", new Class[]{int.class, getNMSClass("NBTBase")}));
            if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_14)) {
                methodCache.put("getTypeId", getNMSClass("NBTBase").getMethod("getTypeId", new Class[0]));
                methodCache.put("add", getNMSClass("NBTTagList").getMethod("add", new Class[]{int.class, getNMSClass("NBTBase")}));
            } else {
                methodCache.put("add", getNMSClass("NBTTagList").getMethod("add", new Class[]{getNMSClass("NBTBase")}));
            }
            methodCache.put("size", getNMSClass("NBTTagList").getMethod("size", new Class[0]));
            if (LOCAL_VERSION == MinecraftVersion.v1_8) {
                methodCache.put("listRemove", getNMSClass("NBTTagList").getMethod("a", new Class[]{int.class}));
            } else {
                methodCache.put("listRemove", getNMSClass("NBTTagList").getMethod("remove", new Class[]{int.class}));
            }
            methodCache.put("remove", getNMSClass("NBTTagCompound").getMethod("remove", new Class[]{String.class}));
            if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                methodCache.put("getKeys", getNMSClass("NBTTagCompound").getMethod("getKeys", new Class[0]));
            } else {
                methodCache.put("getKeys", getNMSClass("NBTTagCompound").getMethod("c", new Class[0]));
            }
            methodCache.put("hasTag", getNMSClass("ItemStack").getMethod("hasTag", new Class[0]));
            methodCache.put("getTag", getNMSClass("ItemStack").getMethod("getTag", new Class[0]));
            methodCache.put("setTag", getNMSClass("ItemStack").getMethod("setTag", new Class[]{getNMSClass("NBTTagCompound")}));
            methodCache.put("asNMSCopy", getNMSClass("CraftItemStack").getMethod("asNMSCopy", new Class[]{ItemStack.class}));
            methodCache.put("asBukkitCopy", getNMSClass("CraftItemStack").getMethod("asBukkitCopy", new Class[]{getNMSClass("ItemStack")}));
            methodCache.put("getEntityHandle", getNMSClass("CraftEntity").getMethod("getHandle", new Class[0]));
            if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                methodCache.put("getEntityTag", getNMSClass("Entity").getMethod("save", new Class[]{getNMSClass("NBTTagCompound")}));
                methodCache.put("setEntityTag", getNMSClass("Entity").getMethod("load", new Class[]{getNMSClass("NBTTagCompound")}));
            } else {
                methodCache.put("getEntityTag", getNMSClass("Entity").getMethod("c", new Class[]{getNMSClass("NBTTagCompound")}));
                methodCache.put("setEntityTag", getNMSClass("Entity").getMethod("f", new Class[]{getNMSClass("NBTTagCompound")}));
            }
            methodCache.put("save", getNMSClass("ItemStack").getMethod("save", new Class[]{getNMSClass("NBTTagCompound")}));
            if (LOCAL_VERSION.lessThanOrEqualTo(MinecraftVersion.v1_10)) {
                methodCache.put("createStack", getNMSClass("ItemStack").getMethod("createStack", new Class[]{getNMSClass("NBTTagCompound")}));
            } else if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                methodCache.put("createStack", getNMSClass("ItemStack").getMethod("a", new Class[]{getNMSClass("NBTTagCompound")}));
            }
            if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                methodCache.put("setTileTag", getNMSClass("TileEntity").getMethod("load", new Class[]{getNMSClass("IBlockData"), getNMSClass("NBTTagCompound")}));
                methodCache.put("getType", getNMSClass("World").getMethod("getType", new Class[]{getNMSClass("BlockPosition")}));
            } else if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_12)) {
                methodCache.put("setTileTag", getNMSClass("TileEntity").getMethod("load", new Class[]{getNMSClass("NBTTagCompound")}));
            } else {
                methodCache.put("setTileTag", getNMSClass("TileEntity").getMethod("a", new Class[]{getNMSClass("NBTTagCompound")}));
            }
            methodCache.put("getTileEntity", getNMSClass("World").getMethod("getTileEntity", new Class[]{getNMSClass("BlockPosition")}));
            methodCache.put("getWorldHandle", getNMSClass("CraftWorld").getMethod("getHandle", new Class[0]));
            methodCache.put("setGameProfile", getNMSClass("TileEntitySkull").getMethod("setGameProfile", new Class[]{getNMSClass("GameProfile")}));
            methodCache.put("getProperties", getNMSClass("GameProfile").getMethod("getProperties", new Class[0]));
            methodCache.put("getName", getNMSClass("Property").getMethod("getName", new Class[0]));
            methodCache.put("getValue", getNMSClass("Property").getMethod("getValue", new Class[0]));
            methodCache.put("values", getNMSClass("PropertyMap").getMethod("values", new Class[0]));
            methodCache.put("put", getNMSClass("PropertyMap").getMethod("put", new Class[]{Object.class, Object.class}));
            methodCache.put("loadNBTTagCompound", getNMSClass("MojangsonParser").getMethod("parse", new Class[]{String.class}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            methodCache.put("getTileTag", getNMSClass("TileEntity").getMethod("save", new Class[]{getNMSClass("NBTTagCompound")}));
        } catch (NoSuchMethodException exception) {
            try {
                methodCache.put("getTileTag", getNMSClass("TileEntity").getMethod("b", new Class[]{getNMSClass("NBTTagCompound")}));
            } catch (Exception exception2) {
                exception2.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            methodCache.put("setProfile", getNMSClass("CraftMetaSkull").getDeclaredMethod("setProfile", new Class[]{getNMSClass("GameProfile")}));
            ((Method) methodCache.get("setProfile")).setAccessible(true);
        } catch (NoSuchMethodException noSuchMethodException) {
        }
        constructorCache = new HashMap<>();
        try {
            constructorCache.put(getNBTTag(Byte.class), getNBTTag(Byte.class).getDeclaredConstructor(new Class[]{byte.class}));
            constructorCache.put(getNBTTag(Boolean.class), getNBTTag(Boolean.class).getDeclaredConstructor(new Class[]{byte.class}));
            constructorCache.put(getNBTTag(String.class), getNBTTag(String.class).getDeclaredConstructor(new Class[]{String.class}));
            constructorCache.put(getNBTTag(Double.class), getNBTTag(Double.class).getDeclaredConstructor(new Class[]{double.class}));
            constructorCache.put(getNBTTag(Integer.class), getNBTTag(Integer.class).getDeclaredConstructor(new Class[]{int.class}));
            constructorCache.put(getNBTTag(Long.class), getNBTTag(Long.class).getDeclaredConstructor(new Class[]{long.class}));
            constructorCache.put(getNBTTag(Float.class), getNBTTag(Float.class).getDeclaredConstructor(new Class[]{float.class}));
            constructorCache.put(getNBTTag(Short.class), getNBTTag(Short.class).getDeclaredConstructor(new Class[]{short.class}));
            constructorCache.put(getNBTTag(Class.forName("[B")), getNBTTag(Class.forName("[B")).getDeclaredConstructor(new Class[]{Class.forName("[B")}));
            constructorCache.put(getNBTTag(Class.forName("[I")), getNBTTag(Class.forName("[I")).getDeclaredConstructor(new Class[]{Class.forName("[I")}));
            for (Constructor<?> cons : constructorCache.values())
                cons.setAccessible(true);
            constructorCache.put(getNMSClass("BlockPosition"), getNMSClass("BlockPosition").getConstructor(new Class[]{int.class, int.class, int.class}));
            constructorCache.put(getNMSClass("GameProfile"), getNMSClass("GameProfile").getConstructor(new Class[]{UUID.class, String.class}));
            constructorCache.put(getNMSClass("Property"), getNMSClass("Property").getConstructor(new Class[]{String.class, String.class}));
            if (LOCAL_VERSION == MinecraftVersion.v1_11 || LOCAL_VERSION == MinecraftVersion.v1_12)
                constructorCache.put(getNMSClass("ItemStack"), getNMSClass("ItemStack").getConstructor(new Class[]{getNMSClass("NBTTagCompound")}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        NBTTagFieldCache = new HashMap<>();
        try {
            for (Class<?> clazz : NBTClasses.values()) {
                Field data = clazz.getDeclaredField("data");
                data.setAccessible(true);
                NBTTagFieldCache.put(clazz, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            NBTListData = getNMSClass("NBTTagList").getDeclaredField("list");
            NBTListData.setAccessible(true);
            NBTCompoundMap = getNMSClass("NBTTagCompound").getDeclaredField("map");
            NBTCompoundMap.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Class<?> getNBTTag(Class<?> primitiveType) {
        if (NBTClasses.containsKey(primitiveType))
            return NBTClasses.get(primitiveType);
        return primitiveType;
    }
    
    private static Object getNBTVar(Object object) {
        if (object == null)
            return null;
        Class<?> clazz = object.getClass();
        try {
            if (NBTTagFieldCache.containsKey(clazz))
                return ((Field) NBTTagFieldCache.get(clazz)).get(object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
    
    private static Method getMethod(String name) {
        return methodCache.containsKey(name) ? methodCache.get(name) : null;
    }
    
    private static Constructor<?> getConstructor(Class<?> clazz) {
        return constructorCache.containsKey(clazz) ? constructorCache.get(clazz) : null;
    }
    
    private static Class<?> getNMSClass(String name) {
        if (classCache.containsKey(name))
            return classCache.get(name);
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String getMatch(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }
    
    private static Object createItemStack(Object compound) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        if (LOCAL_VERSION == MinecraftVersion.v1_11 || LOCAL_VERSION == MinecraftVersion.v1_12)
            return getConstructor(getNMSClass("ItemStack")).newInstance(new Object[]{compound});
        return getMethod("createStack").invoke((Object) null, new Object[]{compound});
    }
    
    public static String getVersion() {
        return VERSION;
    }
    
    public static MinecraftVersion getMinecraftVersion() {
        return LOCAL_VERSION;
    }
    
    public static ItemStack getHead(String skinURL) {
        Material material = Material.getMaterial("SKULL_ITEM");
        if (material == null)
            material = Material.getMaterial("PLAYER_HEAD");
        ItemStack head = new ItemStack(material, 1, (short) 3);
        if (skinURL == null || skinURL.isEmpty())
            return head;
        ItemMeta headMeta = head.getItemMeta();
        Object profile = null;
        try {
            profile = getConstructor(getNMSClass("GameProfile")).newInstance(new Object[]{UUID.randomUUID(), null});
            Object propertyMap = getMethod("getProperties").invoke(profile, new Object[0]);
            Object textureProperty = getConstructor(getNMSClass("Property")).newInstance(new Object[]{"textures", skinURL});
            getMethod("put").invoke(propertyMap, new Object[]{"textures", textureProperty});
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e1) {
            e1.printStackTrace();
        }
        if (methodCache.containsKey("setProfile")) {
            try {
                getMethod("setProfile").invoke(headMeta, new Object[]{profile});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Field profileField = null;
            try {
                profileField = headMeta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            profileField.setAccessible(true);
            try {
                profileField.set(headMeta, profile);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        head.setItemMeta(headMeta);
        return head;
    }
    
    public static String getTexture(ItemStack head) {
        Field profileField;
        ItemMeta meta = head.getItemMeta();
        try {
            profileField = meta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return "";
        }
        profileField.setAccessible(true);
        try {
            Object profile = profileField.get(meta);
            if (profile == null)
                return "";
            Collection<Object> properties = (Collection<Object>) getMethod("values").invoke(getMethod("getProperties").invoke(profile, new Object[0]), new Object[0]);
            for (Object prop : properties) {
                if ("textures".equals(getMethod("getName").invoke(prop, new Object[0])))
                    return (String) getMethod("getValue").invoke(prop, new Object[0]);
            }
            return "";
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    private static Object getItemTag(ItemStack item, Object... keys) {
        try {
            return getTag(getCompound(item), keys);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Object getCompound(ItemStack item) {
        if (item == null)
            return null;
        try {
            Object stack = null;
            stack = getMethod("asNMSCopy").invoke((Object) null, new Object[]{item});
            Object tag = null;
            if (getMethod("hasTag").invoke(stack, new Object[0]).equals(Boolean.valueOf(true))) {
                tag = getMethod("getTag").invoke(stack, new Object[0]);
            } else {
                tag = getNMSClass("NBTTagCompound").newInstance();
            }
            return tag;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    private static NBTCompound getItemNBTTag(ItemStack item, Object... keys) {
        if (item == null)
            return null;
        try {
            Object stack = null;
            stack = getMethod("asNMSCopy").invoke((Object) null, new Object[]{item});
            Object tag = getNMSClass("NBTTagCompound").newInstance();
            tag = getMethod("save").invoke(stack, new Object[]{tag});
            return getNBTTag(tag, keys);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    private static ItemStack setItemTag(ItemStack item, Object value, Object... keys) {
        if (item == null)
            return null;
        try {
            Object stack = getMethod("asNMSCopy").invoke((Object) null, new Object[]{item});
            Object tag = null;
            if (getMethod("hasTag").invoke(stack, new Object[0]).equals(Boolean.valueOf(true))) {
                tag = getMethod("getTag").invoke(stack, new Object[0]);
            } else {
                tag = getNMSClass("NBTTagCompound").newInstance();
            }
            if (keys.length == 0 && value instanceof NBTCompound) {
                tag = ((NBTCompound) value).tag;
            } else {
                setTag(tag, value, keys);
            }
            getMethod("setTag").invoke(stack, new Object[]{tag});
            return (ItemStack) getMethod("asBukkitCopy").invoke((Object) null, new Object[]{stack});
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static ItemStack getItemFromTag(NBTCompound compound) {
        if (compound == null)
            return null;
        try {
            Object tag = compound.tag;
            Object count = getTag(tag, new Object[]{"Count"});
            Object id = getTag(tag, new Object[]{"id"});
            if (count == null || id == null)
                return null;
            if (count instanceof Byte && id instanceof String)
                return (ItemStack) getMethod("asBukkitCopy").invoke((Object) null, new Object[]{createItemStack(tag)});
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    private static Object getEntityTag(Entity entity, Object... keys) {
        try {
            return getTag(getCompound(entity), keys);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Object getCompound(Entity entity) {
        if (entity == null)
            return entity;
        try {
            Object NMSEntity = getMethod("getEntityHandle").invoke(entity, new Object[0]);
            Object tag = getNMSClass("NBTTagCompound").newInstance();
            getMethod("getEntityTag").invoke(NMSEntity, new Object[]{tag});
            return tag;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    private static NBTCompound getEntityNBTTag(Entity entity, Object... keys) {
        if (entity == null)
            return null;
        try {
            Object NMSEntity = getMethod("getEntityHandle").invoke(entity, new Object[0]);
            Object tag = getNMSClass("NBTTagCompound").newInstance();
            getMethod("getEntityTag").invoke(NMSEntity, new Object[]{tag});
            return getNBTTag(tag, keys);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    private static void setEntityTag(Entity entity, Object value, Object... keys) {
        if (entity == null)
            return;
        try {
            Object NMSEntity = getMethod("getEntityHandle").invoke(entity, new Object[0]);
            Object tag = getNMSClass("NBTTagCompound").newInstance();
            getMethod("getEntityTag").invoke(NMSEntity, new Object[]{tag});
            if (keys.length == 0 && value instanceof NBTCompound) {
                tag = ((NBTCompound) value).tag;
            } else {
                setTag(tag, value, keys);
            }
            getMethod("setEntityTag").invoke(NMSEntity, new Object[]{tag});
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }
    
    private static Object getBlockTag(Block block, Object... keys) {
        try {
            return getTag(getCompound(block), keys);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Object getCompound(Block block) {
        try {
            if (block == null || !getNMSClass("CraftBlockState").isInstance(block.getState()))
                return null;
            Location location = block.getLocation();
            Object blockPosition = getConstructor(getNMSClass("BlockPosition")).newInstance(new Object[]{Integer.valueOf(location.getBlockX()), Integer.valueOf(location.getBlockY()), Integer.valueOf(location.getBlockZ())});
            Object nmsWorld = getMethod("getWorldHandle").invoke(location.getWorld(), new Object[0]);
            Object tileEntity = getMethod("getTileEntity").invoke(nmsWorld, new Object[]{blockPosition});
            Object tag = getNMSClass("NBTTagCompound").newInstance();
            getMethod("getTileTag").invoke(tileEntity, new Object[]{tag});
            return tag;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    private static NBTCompound getBlockNBTTag(Block block, Object... keys) {
        try {
            if (block == null || !getNMSClass("CraftBlockState").isInstance(block.getState()))
                return null;
            Location location = block.getLocation();
            Object blockPosition = getConstructor(getNMSClass("BlockPosition")).newInstance(new Object[]{Integer.valueOf(location.getBlockX()), Integer.valueOf(location.getBlockY()), Integer.valueOf(location.getBlockZ())});
            Object nmsWorld = getMethod("getWorldHandle").invoke(location.getWorld(), new Object[0]);
            Object tileEntity = getMethod("getTileEntity").invoke(nmsWorld, new Object[]{blockPosition});
            Object tag = getNMSClass("NBTTagCompound").newInstance();
            getMethod("getTileTag").invoke(tileEntity, new Object[]{tag});
            return getNBTTag(tag, keys);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    private static void setBlockTag(Block block, Object value, Object... keys) {
        try {
            if (block == null || !getNMSClass("CraftBlockState").isInstance(block.getState()))
                return;
            Location location = block.getLocation();
            Object blockPosition = getConstructor(getNMSClass("BlockPosition")).newInstance(new Object[]{Integer.valueOf(location.getBlockX()), Integer.valueOf(location.getBlockY()), Integer.valueOf(location.getBlockZ())});
            Object nmsWorld = getMethod("getWorldHandle").invoke(location.getWorld(), new Object[0]);
            Object tileEntity = getMethod("getTileEntity").invoke(nmsWorld, new Object[]{blockPosition});
            Object tag = getNMSClass("NBTTagCompound").newInstance();
            getMethod("getTileTag").invoke(tileEntity, new Object[]{tag});
            if (keys.length == 0 && value instanceof NBTCompound) {
                tag = ((NBTCompound) value).tag;
            } else {
                setTag(tag, value, keys);
            }
            if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                getMethod("setTileTag").invoke(tileEntity, new Object[]{getMethod("getType").invoke(nmsWorld, new Object[]{blockPosition}), tag});
            } else {
                getMethod("setTileTag").invoke(tileEntity, new Object[]{tag});
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }
    
    public static void setSkullTexture(Block block, String texture) {
        try {
            Object profile = getConstructor(getNMSClass("GameProfile")).newInstance(new Object[]{UUID.randomUUID(), null});
            Object propertyMap = getMethod("getProperties").invoke(profile, new Object[0]);
            Object textureProperty = getConstructor(getNMSClass("Property")).newInstance(new Object[]{"textures", new String(Base64.getEncoder().encode(String.format("{textures:{SKIN:{\"url\":\"%s\"}}}", new Object[]{texture}).getBytes()))});
            getMethod("put").invoke(propertyMap, new Object[]{"textures", textureProperty});
            Location location = block.getLocation();
            Object blockPosition = getConstructor(getNMSClass("BlockPosition")).newInstance(new Object[]{Integer.valueOf(location.getBlockX()), Integer.valueOf(location.getBlockY()), Integer.valueOf(location.getBlockZ())});
            Object nmsWorld = getMethod("getWorldHandle").invoke(location.getWorld(), new Object[0]);
            Object tileEntity = getMethod("getTileEntity").invoke(nmsWorld, new Object[]{blockPosition});
            getMethod("setGameProfile").invoke(tileEntity, new Object[]{profile});
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    private static Object getValue(Object object, Object... keys) {
        if (object instanceof ItemStack)
            return getItemTag((ItemStack) object, keys);
        if (object instanceof Entity)
            return getEntityTag((Entity) object, keys);
        if (object instanceof Block)
            return getBlockTag((Block) object, keys);
        if (object instanceof NBTCompound)
            try {
                return getTag(((NBTCompound) object).tag, keys);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
    }
    
    public static NBTCompound getNBTCompound(Object object, Object... keys) {
        if (object instanceof ItemStack)
            return getItemNBTTag((ItemStack) object, keys);
        if (object instanceof Entity)
            return getEntityNBTTag((Entity) object, keys);
        if (object instanceof Block)
            return getBlockNBTTag((Block) object, keys);
        if (object instanceof NBTCompound)
            try {
                return getNBTTag(((NBTCompound) object).tag, keys);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        if (getNMSClass("NBTTagCompound").isInstance(object))
            try {
                return getNBTTag(object, keys);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
    }
    
    public static String getString(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof String) ? (String) result : null;
    }
    
    public static int getInt(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof Integer) ? ((Integer) result).intValue() : 0;
    }
    
    public static double getDouble(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof Double) ? ((Double) result).doubleValue() : 0.0D;
    }
    
    public static long getLong(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof Long) ? ((Long) result).longValue() : 0L;
    }
    
    public static float getFloat(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof Float) ? ((Float) result).floatValue() : 0.0F;
    }
    
    public static short getShort(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof Short) ? ((Short) result).shortValue() : 0;
    }
    
    public static byte getByte(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof Byte) ? ((Byte) result).byteValue() : 0;
    }
    
    public static boolean getBoolean(Object object, Object... keys) {
        return (getByte(object, keys) == 1);
    }
    
    public static byte[] getByteArray(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof byte[]) ? (byte[]) result : null;
    }
    
    public static int[] getIntArray(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result instanceof int[]) ? (int[]) result : null;
    }
    
    public static boolean contains(Object object, Object... keys) {
        Object result = getValue(object, keys);
        return (result != null);
    }
    
    public static Collection<String> getKeys(Object object, Object... keys) {
        Object compound;
        if (object instanceof ItemStack) {
            compound = getCompound((ItemStack) object);
        } else if (object instanceof Entity) {
            compound = getCompound((Entity) object);
        } else if (object instanceof Block) {
            compound = getCompound((Block) object);
        } else if (object instanceof NBTCompound) {
            compound = ((NBTCompound) object).tag;
        } else {
            throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
        }
        try {
            NBTCompound nbtCompound = getNBTTag(compound, keys);
            Object tag = nbtCompound.tag;
            if (getNMSClass("NBTTagCompound").isInstance(tag))
                return (Collection<String>) getMethod("getKeys").invoke(tag, new Object[0]);
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static int getSize(Object object, Object... keys) {
        Object compound;
        if (object instanceof ItemStack) {
            compound = getCompound((ItemStack) object);
        } else if (object instanceof Entity) {
            compound = getCompound((Entity) object);
        } else if (object instanceof Block) {
            compound = getCompound((Block) object);
        } else if (object instanceof NBTCompound) {
            compound = ((NBTCompound) object).tag;
        } else {
            throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
        }
        try {
            NBTCompound nbtCompound = getNBTTag(compound, keys);
            if (getNMSClass("NBTTagCompound").isInstance(nbtCompound.tag))
                return getKeys(nbtCompound, new Object[0]).size();
            if (getNMSClass("NBTTagList").isInstance(nbtCompound.tag))
                return ((Integer) getMethod("size").invoke(nbtCompound.tag, new Object[0])).intValue();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return 0;
        }
        throw new IllegalArgumentException("Value is not a compound or list!");
    }
    
    public static <T> T set(T object, Object value, Object... keys) {
        if (object instanceof ItemStack)
            return (T) setItemTag((ItemStack) object, value, keys);
        if (object instanceof Entity) {
            setEntityTag((Entity) object, value, keys);
        } else if (object instanceof Block) {
            setBlockTag((Block) object, value, keys);
        } else if (object instanceof NBTCompound) {
            try {
                setTag(((NBTCompound) object).tag, value, keys);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
        }
        return object;
    }
    
    public static NBTCompound getNBTCompound(String json) {
        return NBTCompound.fromJson(json);
    }
    
    public static NBTCompound getEmptyNBTCompound() {
        try {
            return new NBTCompound(getNMSClass("NBTTagCompound").newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static void setTag(Object tag, Object value, Object... keys) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object notCompound;
        if (value != null) {
            if (value instanceof NBTCompound) {
                notCompound = ((NBTCompound) value).tag;
            } else if (getNMSClass("NBTTagList").isInstance(value) || getNMSClass("NBTTagCompound").isInstance(value)) {
                notCompound = value;
            } else {
                if (value instanceof Boolean)
                    value = Byte.valueOf((byte) ((((Boolean) value).booleanValue() == true) ? 1 : 0));
                notCompound = getConstructor(getNBTTag(value.getClass())).newInstance(new Object[]{value});
            }
        } else {
            notCompound = null;
        }
        Object compound = tag;
        for (int index = 0; index < keys.length - 1; index++) {
            Object key = keys[index];
            Object oldCompound = compound;
            if (key instanceof Integer) {
                compound = ((List) NBTListData.get(compound)).get(((Integer) key).intValue());
            } else if (key != null) {
                compound = getMethod("get").invoke(compound, new Object[]{key});
            }
            if (compound == null || key == null) {
                if (keys[index + 1] == null || keys[index + 1] instanceof Integer) {
                    compound = getNMSClass("NBTTagList").newInstance();
                } else {
                    compound = getNMSClass("NBTTagCompound").newInstance();
                }
                if (oldCompound.getClass().getSimpleName().equals("NBTTagList")) {
                    if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_14)) {
                        getMethod("add").invoke(oldCompound, new Object[]{getMethod("size").invoke(oldCompound, new Object[0]), compound});
                    } else {
                        getMethod("add").invoke(oldCompound, new Object[]{compound});
                    }
                } else {
                    getMethod("set").invoke(oldCompound, new Object[]{key, compound});
                }
            }
        }
        if (keys.length > 0) {
            Object lastKey = keys[keys.length - 1];
            if (lastKey == null) {
                if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_14)) {
                    getMethod("add").invoke(compound, new Object[]{getMethod("size").invoke(compound, new Object[0]), notCompound});
                } else {
                    getMethod("add").invoke(compound, new Object[]{notCompound});
                }
            } else if (lastKey instanceof Integer) {
                if (notCompound == null) {
                    getMethod("listRemove").invoke(compound, new Object[]{Integer.valueOf(((Integer) lastKey).intValue())});
                } else {
                    getMethod("setIndex").invoke(compound, new Object[]{Integer.valueOf(((Integer) lastKey).intValue()), notCompound});
                }
            } else if (notCompound == null) {
                getMethod("remove").invoke(compound, new Object[]{lastKey});
            } else {
                getMethod("set").invoke(compound, new Object[]{lastKey, notCompound});
            }
        } else if (notCompound != null) {
        
        }
    }
    
    private static NBTCompound getNBTTag(Object tag, Object... keys) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object compound = tag;
        for (Object key : keys) {
            if (compound == null)
                return null;
            if (getNMSClass("NBTTagCompound").isInstance(compound)) {
                compound = getMethod("get").invoke(compound, new Object[]{key});
            } else if (getNMSClass("NBTTagList").isInstance(compound)) {
                compound = ((List) NBTListData.get(compound)).get(((Integer) key).intValue());
            }
        }
        return new NBTCompound(compound);
    }
    
    private static Object getTag(Object tag, Object... keys) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (keys.length == 0)
            return getTags(tag);
        Object notCompound = tag;
        for (Object key : keys) {
            if (notCompound == null)
                return null;
            if (getNMSClass("NBTTagCompound").isInstance(notCompound)) {
                notCompound = getMethod("get").invoke(notCompound, new Object[]{key});
            } else if (getNMSClass("NBTTagList").isInstance(notCompound)) {
                notCompound = ((List) NBTListData.get(notCompound)).get(((Integer) key).intValue());
            } else {
                return getNBTVar(notCompound);
            }
        }
        if (notCompound == null)
            return null;
        if (getNMSClass("NBTTagList").isInstance(notCompound))
            return getTags(notCompound);
        if (getNMSClass("NBTTagCompound").isInstance(notCompound))
            return getTags(notCompound);
        return getNBTVar(notCompound);
    }
    
    private static Object getTags(Object tag) {
        Map<Object, Object> tags = new HashMap<>();
        try {
            if (getNMSClass("NBTTagCompound").isInstance(tag)) {
                Map<String, Object> tagCompound = (Map<String, Object>) NBTCompoundMap.get(tag);
                for (String key : tagCompound.keySet()) {
                    Object value = tagCompound.get(key);
                    if (getNMSClass("NBTTagEnd").isInstance(value))
                        continue;
                    tags.put(key, getTag(value, new Object[0]));
                }
            } else if (getNMSClass("NBTTagList").isInstance(tag)) {
                List<Object> tagList = (List<Object>) NBTListData.get(tag);
                for (int index = 0; index < tagList.size(); index++) {
                    Object value = tagList.get(index);
                    if (!getNMSClass("NBTTagEnd").isInstance(value))
                        tags.put(Integer.valueOf(index), getTag(value, new Object[0]));
                }
            } else {
                return getNBTVar(tag);
            }
            return tags;
        } catch (Exception e) {
            e.printStackTrace();
            return tags;
        }
    }
    
    public enum MinecraftVersion {
        v1_8("1_8", 0),
        v1_9("1_9", 1),
        v1_10("1_10", 2),
        v1_11("1_11", 3),
        v1_12("1_12", 4),
        v1_13("1_13", 5),
        v1_14("1_14", 6),
        v1_15("1_15", 7),
        v1_16("1_16", 8),
        v1_17("1_17", 9),
        v1_18("1_18", 10),
        v1_19("1_19", 11);
        
        private int order;
        
        private String key;
        
        MinecraftVersion(String key, int v) {
            this.key = key;
            this.order = v;
        }
        
        public static MinecraftVersion get(String v) {
            for (MinecraftVersion k : values()) {
                if (v.contains(k.key))
                    return k;
            }
            return null;
        }
        
        public boolean greaterThanOrEqualTo(MinecraftVersion other) {
            return (this.order >= other.order);
        }
        
        public boolean lessThanOrEqualTo(MinecraftVersion other) {
            return (this.order <= other.order);
        }
    }
    
    public static final class NBTCompound {
        
        protected final Object tag;
        
        protected NBTCompound(@Nonnull Object tag) {
            this.tag = tag;
        }
        
        public static NBTCompound fromJson(String json) {
            try {
                return new NBTCompound(NBTEditor.getMethod("loadNBTTagCompound").invoke((Object) null, new Object[]{json}));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        public void set(Object value, Object... keys) {
            try {
                NBTEditor.setTag(this.tag, value, keys);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public String toJson() {
            return this.tag.toString();
        }
        
        public String toString() {
            return this.tag.toString();
        }
        
        public int hashCode() {
            return this.tag.hashCode();
        }
        
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            NBTCompound other = (NBTCompound) obj;
            if (this.tag == null) {
                if (other.tag != null)
                    return false;
            } else if (!this.tag.equals(other.tag)) {
                return false;
            }
            return true;
        }
    }
}
