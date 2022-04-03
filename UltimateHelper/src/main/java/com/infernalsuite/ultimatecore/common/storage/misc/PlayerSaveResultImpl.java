package com.infernalsuite.ultimatecore.common.storage.misc;

import com.google.common.collect.*;
import com.infernalsuite.ultimatecore.api.player.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class PlayerSaveResultImpl implements PlayerSaveResult {

    private static final PlayerSaveResultImpl CLEAN_INSERT = new PlayerSaveResultImpl(Outcome.CLEAN_INSERT);
    private static final PlayerSaveResultImpl NO_CHANGE = new PlayerSaveResultImpl(Outcome.NO_CHANGE);

    public static PlayerSaveResultImpl cleanInsert() { return CLEAN_INSERT; }

    public static PlayerSaveResultImpl noChange() { return NO_CHANGE; }

    public static PlayerSaveResultImpl usernameUpdated(String oldUsername) {
        return new PlayerSaveResultImpl(EnumSet.of(Outcome.USERNAME_UPDATED), oldUsername, null);
    }

    public static PlayerSaveResultImpl determineBaseResult(String username, String oldUsername) {
        PlayerSaveResultImpl result;
        if (oldUsername == null) {
            result = PlayerSaveResultImpl.cleanInsert();
        } else if (oldUsername.equalsIgnoreCase(username)) {
            result = PlayerSaveResultImpl.noChange();
        } else {
            result = PlayerSaveResultImpl.usernameUpdated(oldUsername);
        }
        return result;
    }

    @Getter
    private final Set<Outcome> outcomes;
    @Getter
    private final @Nullable String previousUsername;
    @Getter
    private final @Nullable Set<UUID> otherUUIDs;

    private PlayerSaveResultImpl(EnumSet<Outcome> outcomes, @Nullable String previousUsername, @Nullable Set<UUID> otherUUIDs) {
        this.outcomes = ImmutableSet.copyOf(outcomes);
        this.previousUsername = previousUsername;
        this.otherUUIDs = otherUUIDs;
    }

    private PlayerSaveResultImpl(Outcome outcome) { this(EnumSet.of(outcome), null, null); }

    public PlayerSaveResultImpl withOtherUUIDsPresent(@NonNull Set<UUID> otherUUIDs) {
        EnumSet<Outcome> outcomes = EnumSet.copyOf(this.outcomes);
        outcomes.add(Outcome.OTHER_UUIDS_PRESENT_FOR_USERNAME);
        return new PlayerSaveResultImpl(outcomes, this.previousUsername, ImmutableSet.copyOf(otherUUIDs));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSaveResultImpl result = (PlayerSaveResultImpl) o;
        return Objects.equals(outcomes, result.outcomes) &&
                Objects.equals(previousUsername, result.previousUsername) &&
                Objects.equals(otherUUIDs, result.otherUUIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outcomes, previousUsername, otherUUIDs);
    }

    @Override
    public String toString() {
        return "PlayerSaveResultImpl{" +
                "outcomes=" + outcomes +
                ", previousUsername='" + previousUsername + '\'' +
                ", otherUUIDs=" + otherUUIDs +
                '}';
    }
}
