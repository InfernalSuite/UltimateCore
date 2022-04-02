package com.infernalsuite.ultimatecore.common;

import com.google.inject.*;
import com.infernalsuite.ultimatecore.common.di.*;
import me.lucko.helper.plugin.ap.Plugin;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

@Plugin(
        name = "UltimateCore",
        version = "6.0.0",
        description = "UltimateCore",
        load = PluginLoadOrder.STARTUP,
        authors = {"InfernalSuite"},
        apiVersion = "1.18"
)
public class UCHelper extends JavaPlugin {

    @Override
    public void onEnable() {
        final Injector injector = Guice.createInjector(new GuiceModule(this));
        injector.injectMembers(this);
    }

}
