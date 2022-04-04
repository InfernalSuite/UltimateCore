package com.infernalsuite.ultimatecore.common.player;

import com.infernalsuite.ultimatecore.common.api.implementation.*;
import lombok.*;
import mc.ultimatecore.helper.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

public class UCPlayerImpl {

    @Getter
    private final ApiUCPlayer apiProxy = new ApiUCPlayer(this);

    @Getter
    private final UltimatePlugin plugin;

    @Getter
    private final UUID uuid;

    private @Nullable String username = null;

    public UCPlayerImpl(UUID uniqueID, UltimatePlugin plugin) {
        this.uuid = uniqueID;
        this.plugin = plugin;
    }

    public Optional<String> getUsername() { return Optional.ofNullable(this.username); }

    public boolean setUsername(String name, boolean weak) {
        if (name != null && name.length() > 16) return false;

        if (weak && this.username != null) {
            if (this.username.equalsIgnoreCase(name)) {
                this.username = name;
            }
            return false;
        }

        if (name != null && (name.isEmpty() || name.equalsIgnoreCase("null"))) {
            name = null;
        }

        if ((this.username == null) != (name == null)) {
            this.username = name;
            return true;
        }

        if (this.username == null) {
            return false;
        } else {
            if (this.username.equalsIgnoreCase(name)) {
                this.username = name;
                return false;
            } else {
                this.username = name;
                return true;
            }
        }
    }

}
