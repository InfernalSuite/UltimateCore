package mc.ultimatecore.farm.skullcreator;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class SkullCreator {
    private static Method GET_PROPERTIES;

    private static Method INSERT_PROPERTY;

    private static Constructor<?> GAME_PROFILE_CONSTRUCTOR;

    private static Constructor<?> PROPERTY_CONSTRUCTOR;

    static {
        try {
            Class<?> gameProfile = Class.forName("com.mojang.authlib.GameProfile");
            Class<?> property = Class.forName("com.mojang.authlib.properties.Property");
            Class<?> propertyMap = Class.forName("com.mojang.authlib.properties.PropertyMap");
            GAME_PROFILE_CONSTRUCTOR = getConstructor(gameProfile, 2);
            PROPERTY_CONSTRUCTOR = getConstructor(property, 2);
            GET_PROPERTIES = getMethod(gameProfile, "getProperties");
            INSERT_PROPERTY = getMethod(propertyMap, "put");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Method getMethod(Class<?> clazz, String name) {
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(name))
                return m;
        }
        return null;
    }

    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field f = getField(object.getClass(), fieldName);
        f.setAccessible(true);
        f.set(object, value);
    }

    public static Constructor<?> getConstructor(Class<?> clazz, int numParams) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if ((constructor.getParameterTypes()).length == numParams)
                return constructor;
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack getSkull(String texture) {
        ItemStack playerHead;
        texture = texture.replace(" ", "");
        if (texture.length() > 16)
            try {
                ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
                ItemMeta meta = skull.getItemMeta();
                try {
                    Object profile = GAME_PROFILE_CONSTRUCTOR.newInstance(UUID.randomUUID(), UUID.randomUUID().toString().substring(17).replace("-", ""));
                    Object properties = GET_PROPERTIES.invoke(profile);
                    INSERT_PROPERTY.invoke(properties, "textures", PROPERTY_CONSTRUCTOR.newInstance("textures", texture));
                    setFieldValue(meta, "profile", profile);
                } catch (Exception e) {
                    System.err.println("Failed to create fake GameProfile for custom player head:");
                    e.printStackTrace();
                }
                skull.setItemMeta(meta);
                return skull;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        if (texture.contains("hdb-"))
            try {
                return HeadDatabase.headApi.getItemHead(texture.replace("hdb-", ""));
            } catch (Exception e) {
                texture = "mhf_question";
            }
        playerHead = XMaterial.PLAYER_HEAD.parseItem();

        SkullMeta sm = (SkullMeta) playerHead.getItemMeta();
        sm.setOwner(texture);
        playerHead.setItemMeta(sm);
        return playerHead;
    }
}