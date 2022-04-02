package com.infernalsuite.ultimatecore.api;

/**
 * The main entry point for the UltimateCore API(s).
 *
 * <p>The APIs allow other plugins or services to interact with UltimateCore, such as listening to events, changing UltimateCore's
 * behaviour, and integrating UltimateCore with other systems.</p>
 *
 * <p>This interface represents the base of the API package. All core API functions are accessed via this interface.</p>
 *
 * <p>To use the API, you need to obtain an instance of this interface. These are registered by UltimateCore to the platform's Services Manager.
 * This is the preferred method for obtaining an instance.</p>
 *
 * <p>For ease of use, and for platforms without a Services Manager, an instance can also be obtained from the static singleton
 * accessor in {@link UCAPIProvider}</p>
 */
public interface UltimateCoreAPI {
}
