package com.infernalsuite.ultimatecore.common.api.implementation;

import com.google.common.base.*;
import com.infernalsuite.ultimatecore.api.player.*;
import com.infernalsuite.ultimatecore.common.player.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class ApiUCPlayer implements UCPlayer {

    public static UCPlayerImpl cast(UCPlayer player) {
        Preconditions.checkState(player instanceof ApiUCPlayer, "Illegal instance " + player.getClass() + " cannot be handled by this implementation.");
        return ((ApiUCPlayer) player).getHandle();
    }

    @Getter
    private final UCPlayerImpl handle;

    public ApiUCPlayer(UCPlayerImpl handle) {
        this.handle = handle;
    }

    @Override
    public @NonNull UUID getUniqueId() {
        return this.handle.getUuid();
    }

    @Override
    public @Nullable String getUsername() {
        return this.handle.getUsername().orElse(null);
    }
}
