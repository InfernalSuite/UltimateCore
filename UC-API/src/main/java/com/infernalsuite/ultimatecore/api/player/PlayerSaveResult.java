package com.infernalsuite.ultimatecore.api.player;

import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.*;

import java.util.*;

public interface PlayerSaveResult {

    /**
     * Gets the outcomes returned by the operation
     * @return the set of outcomes
     */
    @NonNull @Unmodifiable Set<Outcome> getOutcomes();

    /**
     * Gets if the result includes a certain outcome.
     * @param outcome the outcome to check for
     * @return {@code true} if the result includes the outcome
     */
    default boolean includes(@NonNull Outcome outcome) {
        Objects.requireNonNull(outcome, "outcome cannot be null");
        return getOutcomes().contains(outcome);
    }

    /**
     * Gets the previous username involved in the result.
     * @apiNote Returns {@code null} when the result doesn't {@link #includes(Outcome) include} the
     * {@link Outcome#USERNAME_UPDATED Username Updated} status
     * @see Outcome#USERNAME_UPDATED
     * @return the previous username
     */
    @Nullable String getPreviousName();

    /**
     * Gets the other UUIDs involved in the result.
     * @apiNote returns {@code null} when the result doesn't {@link #includes(Outcome) include} the
     * {@link Outcome#OTHER_UUIDS_PRESENT_FOR_USERNAME Other UUIDs} status.
     * @see Outcome#OTHER_UUIDS_PRESENT_FOR_USERNAME
     * @return the other UUIDs
     */
    @Nullable @Unmodifiable Set<UUID> getOtherUUIDs();

    enum Outcome {

        /**
         * There was no existing data saved for either the UUID or username.
         */
        CLEAN_INSERT,

        /**
         * There was existing data for the player, no change was needed.
         */
        NO_CHANGE,

        /**
         * There was already a record for the UUID saved, but for a different username.
         * <p>This is normal - players are able to change their usernames.</p>
         */
        USERNAME_UPDATED,

        /**
         * There was already a record for the username saved, but it was under a different UUID.
         * <p>This is a cause for concern. This could be due to a username swap between players, but is more
         * likely to be due to the server not authenticating correctly (e.g. IP Forwarding) or servers in a network
         * are using a shared database and not all are in online mode.</p>
         */
        OTHER_UUIDS_PRESENT_FOR_USERNAME
    }

}
