package mc.ultimatecore.dragon.utils.particle;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {

    public Reflection() {
    }

    public static Class<?> getNMSClass(String name) {
        return getClass("net.minecraft.server." + getVersion() + "." + name);
    }

    public static Class<?> getCraftClass(String name) {
        return getClass("org.bukkit.craftbukkit." + getVersion() + "." + name);
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    public static String getRawVersion() {
        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        return pkg.substring(pkg.lastIndexOf(".") + 1);
    }

    public static String getNMSVersion() {
        return "net.minecraft.server." + getRawVersion() + ".";
    }

    public static Class<?> wrapperToPrimitive(Class<?> clazz) {
        if (clazz == Boolean.class) {
            return Boolean.TYPE;
        } else if (clazz == Integer.class) {
            return Integer.TYPE;
        } else if (clazz == Double.class) {
            return Double.TYPE;
        } else if (clazz == Float.class) {
            return Float.TYPE;
        } else if (clazz == Long.class) {
            return Long.TYPE;
        } else if (clazz == Short.class) {
            return Short.TYPE;
        } else if (clazz == Byte.class) {
            return Byte.TYPE;
        } else if (clazz == Void.class) {
            return Void.TYPE;
        } else {
            return clazz == Character.class ? Character.TYPE : clazz;
        }
    }

    public static Class<?>[] toParamTypes(Object... params) {
        Class<?>[] classes = new Class[params.length];

        for (int i = 0; i < params.length; ++i) {
            classes[i] = wrapperToPrimitive(params[i].getClass());
        }

        return classes;
    }

    public static Enum<?> getEnum(String enumFullName) {
        String[] x = enumFullName.split("\\.(?=[^\\.]+$)");
        if (x.length == 2) {
            String enumClassName = x[0];
            String enumName = x[1];
            Class<Enum> cl = (Class<Enum>) getClass(enumClassName);
            return Enum.valueOf(cl, enumName);
        } else {
            return null;
        }
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static void setValue(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static Object getValue(Object o, String fieldName) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            return field.get(o);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Object callMethod(Object object, String method, Object... params) {
        try {
            Class<?> clazz = object.getClass();
            Method m = clazz.getDeclaredMethod(method, toParamTypes(params));
            m.setAccessible(true);
            return m.invoke(object, params);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Object o, String methodName, Class<?>... params) {
        try {
            Method method = o.getClass().getMethod(methodName, params);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }

            return method;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Field getField(Field field) {
        field.setAccessible(true);
        return field;
    }
}
