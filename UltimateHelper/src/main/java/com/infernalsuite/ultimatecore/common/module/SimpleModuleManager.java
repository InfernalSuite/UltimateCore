package com.infernalsuite.ultimatecore.common.module;

import com.google.gson.*;
import com.google.inject.*;
import com.infernalsuite.ultimatecore.common.*;
import com.infernalsuite.ultimatecore.common.api.module.*;
import com.infernalsuite.ultimatecore.common.util.gson.*;
import org.checkerframework.checker.nullness.qual.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.stream.*;

public class SimpleModuleManager implements ModuleManager, AutoCloseable {

    private final Set<LoadedModule> modules = new HashSet<>();
    private final UCHelper plugin;

    @Inject
    public SimpleModuleManager(UCHelper plugin) { this.plugin = plugin; }

    @Override
    public void close() throws Exception {
        for (LoadedModule module : this.modules) {
            try {
                module.instance.onDisable();
            } catch (Exception e) {
                this.plugin.getLogger().warning("Exception unloading module");
                e.printStackTrace();
            }
        }
        this.modules.clear();
    }

    @Override
    public void loadModule(@NonNull UCModule module) {
        if (this.modules.stream().anyMatch(m -> m.instance.equals(module))) return;
        this.plugin.getLogger().info("Loading module: " + module.getClass().getName());
        this.modules.add(new LoadedModule(module, null));
        module.onLoad();
    }

    public void loadModules(Path directory) {
        if (!Files.exists(directory) || !Files.isDirectory(directory)) return;

        try (Stream<Path> stream = Files.list(directory)) {
            stream.forEach(path -> {
                if (path.getFileName().toString().endsWith(".jar")) {
                    try {
                        loadModule(path);
                    } catch (Exception e) {
                        this.plugin.getLogger().warning("Exception loading module from " + path);
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            this.plugin.getLogger().warning("Exception loading extensions from " + directory);
            e.printStackTrace();
        }
    }

    @Override
    public @NonNull UCModule loadModule(Path path) throws IOException {
        if (this.modules.stream().anyMatch(m -> path.equals(m.path))) {
            throw new IllegalStateException("Module at path " + path + " already loaded.");
        }

        if (!Files.exists(path)) {
            throw new NoSuchFileException("No file at " + path);
        }

        String className;
        try (JarFile jar = new JarFile(path.toFile())) {
            JarEntry moduleJarEntry = jar.getJarEntry("module.json");
            if (moduleJarEntry == null) throw new IllegalStateException("module.json not present");
            try (InputStream in = jar.getInputStream(moduleJarEntry)) {
                if (in == null) throw new IllegalStateException("module.json not present");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                    JsonObject parsed = GsonProvider.parser().parse(reader).getAsJsonObject();
                    className = parsed.get("class").getAsString();
                }
            }
        }

        if (className == null) throw new IllegalArgumentException("className is null");

        Class<? extends UCModule> moduleClass;
        try {
            moduleClass = Class.forName(className).asSubclass(UCModule.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.plugin.getLogger().info("Loading module: " + moduleClass.getName() + " (" + path.getFileName().toString() + ")");

        UCModule module;

        try {
            Constructor<? extends UCModule> ctor = moduleClass.getConstructor();
            module = ctor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find valid constructor in " + moduleClass.getName());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        this.modules.add(new LoadedModule(module, path));
        module.onLoad();

        return module;
    }

    @Override
    public @NonNull @Unmodifiable Collection<UCModule> getLoadedModules() {
        return this.modules.stream().map(m -> m.instance).collect(Collectors.toSet());
    }

    private static final class LoadedModule {
        private final UCModule instance;
        private final Path path;

        private LoadedModule(UCModule module, Path path) {
            this.instance = module;
            this.path = path;
        }
    }

}
