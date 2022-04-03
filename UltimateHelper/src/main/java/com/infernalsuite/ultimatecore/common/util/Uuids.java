package com.infernalsuite.ultimatecore.common.util;

import lombok.experimental.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

@UtilityClass
public class Uuids {

    public final Predicate<String> PREDICATE = s -> parse(s) != null;

    public @Nullable UUID fromString(String string) {
        try {
            return UUID.fromString(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public @Nullable UUID parse(String string) {
        UUID uuid = fromString(string);
        if (uuid == null && string.length() == 32) {
            try {
                uuid = new UUID(
                        Long.parseUnsignedLong(string.substring(0, 16), 16),
                        Long.parseUnsignedLong(string.substring(16), 16)
                );
            } catch (NumberFormatException ignored) {}
        }
        return uuid;
    }

}
