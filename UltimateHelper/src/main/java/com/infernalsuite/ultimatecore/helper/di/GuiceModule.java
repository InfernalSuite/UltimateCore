package com.infernalsuite.ultimatecore.helper.di;

import com.google.inject.*;
import com.google.inject.matcher.*;
import com.google.inject.spi.*;
import com.infernalsuite.ultimatecore.helper.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;
import org.checkerframework.checker.nullness.qual.*;

import java.lang.reflect.*;
import java.util.*;

public class GuiceModule extends AbstractModule implements TypeListener {

    private final @NonNull UCHelper plugin;

    public GuiceModule(final @NonNull UCHelper plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bindListener(Matchers.any(), this);

        bind(UCHelper.class).toInstance(plugin);
        bind(JavaPlugin.class).toInstance(plugin);
        bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());
    }

    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        encounter.register((InjectionListener<I>) i ->
                Arrays.stream(i.getClass().getMethods())
                        .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                        .forEach(method -> invokeMethod(method, i)));
    }

    private void invokeMethod(final @NonNull Method method, final @NonNull Object object) {
        try {
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
