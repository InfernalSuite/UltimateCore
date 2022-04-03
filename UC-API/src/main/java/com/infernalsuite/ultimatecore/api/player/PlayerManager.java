package com.infernalsuite.ultimatecore.api.player;

import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * Represents the manager responsible for {@link UCPlayer} instances.
 *
 * <p>Note that UCPlayer instances are automatically loaded for online players. It's likely that offline players will not have
 * an instance pre-loaded.</p>
 *
 * <p>All blocking methods return {@link CompletableFuture}s. Care should be taken when using such methods to ensure that the
 * main server thread is not blocked.</p>
 */
public interface PlayerManager {

    /**
     * Loads a player from the plugin's storage provider into memory.
     * @param uuid the uuid of the player
     * @param username the username, if known
     * @return the player
     * @throws NullPointerException if the uuid is null
     */
    @NonNull CompletableFuture<UCPlayer> loadPlayer(@NonNull UUID uuid, @Nullable String username);

    /**
     * Loads a player from the plugin's storage provider into memory.
     * @param uuid the uuid of the player
     * @return the player
     * @throws NullPointerException if the uuid is null
     */
    default @NonNull CompletableFuture<UCPlayer> loadPlayer(@NonNull UUID uuid) { return loadPlayer(uuid, null); }

    /**
     * Saves a player's data back to the plugin's storage provider.
     * <p>Should be called after any changes are made to a user.</p>
     * @see #modifyPlayer(UUID, Consumer)
     * @param player the player to save
     * @return a future encapsulating the save operation
     * @throws NullPointerException if player is null
     * @throws IllegalStateException if the user instance was not obtained from UltimateCore
     */
    @NonNull CompletableFuture<Void> savePlayer(@NonNull UCPlayer player);

    /**
     * Loads a player from the plugin's storage provider, applies the given action, then saves the player's data back to storage.
     * <p>This method effectively calls {@link #loadPlayer(UUID)}, followed by the {@code action}, then {@link #savePlayer(UCPlayer)}, and
     * returns an encapsulation of the entire process as a {@link CompletableFuture}.</p>
     * @apiNote This default method is overridden in the implementation, and exists to demonstrate its functionality in the API sources.
     * @param uuid the uuid of the player
     * @param action the action to apply to the player
     * @return a future to encapsulate the operation
     */
    default @NonNull CompletableFuture<Void> modifyPlayer(@NonNull UUID uuid, @NonNull Consumer<? super UCPlayer> action) {
        return loadPlayer(uuid)
                .thenApplyAsync(player -> { action.accept(player); return player; })
                .thenCompose(this::savePlayer);
    }

    /**
     * Gets a loaded player.
     * @param uuid the uuid of the player to get
     * @return a {@link UCPlayer} object, if one matching the uuid is loaded, or {@code null} if not
     * @throws NullPointerException if the uuid is null
     */
    @Nullable UCPlayer getPlayer(@NonNull UUID uuid);

    /**
     * Gets a loaded player.
     * @param username the username of the player to get
     * @return a {@link UCPlayer}, if one matching the username is loaded, or {@code null} if not
     * @throws NullPointerException if the username is null
     */
    @Nullable UCPlayer getPlayer(@NonNull String username);

    /**
     * Gets a set of all loaded players
     * @return a {@link Set} of {@link UCPlayer}
     */
    @NonNull @Unmodifiable Set<UCPlayer> getLoadedUsers();

    /**
     * Check if a player is loaded in memory.
     * @param uuid the uuid to check for
     * @return {@code true} if the player is loaded
     * @throws NullPointerException if the uuid is null
     */
    boolean isLoaded(@NonNull UUID uuid);

    /**
     * Unload a player from the internal storage, if they're not currently online
     * @param player the player to unload
     * @throws NullPointerException if the player is null
     */
    void cleanupUser(@NonNull UCPlayer player);

}
