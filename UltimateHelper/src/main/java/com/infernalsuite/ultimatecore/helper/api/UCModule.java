package com.infernalsuite.ultimatecore.helper.api;

/**
 * Represents a "plugin" within UltimateCore
 */
public interface UCModule {

    /**
     * Called when the plugins are being loaded, before enabling
     */
    void onLoad();

    /**
     * Called to enable the plugin
     */
    void onEnable();

    /**
     * Called to disable the plugin
     */
    void onDisable();

}
