package com.infernalsuite.ultimatecore.common.storage.implementation.file;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.infernalsuite.ultimatecore.api.player.*;
import com.infernalsuite.ultimatecore.common.storage.misc.*;
import com.infernalsuite.ultimatecore.common.util.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class FileUuidCache {

    private static final Splitter KV_SPLIT = Splitter.on(':').omitEmptyStrings();

    private final LookupMap lookupMap = new LookupMap();

    public PlayerSaveResult addMapping(UUID uuid, String username) {
        String oldUsername = this.lookupMap.put(uuid, username);

        PlayerSaveResultImpl result = PlayerSaveResultImpl.determineBaseResult(username, oldUsername);

        Set<UUID> conflicting = new HashSet<>(this.lookupMap.lookupUUID(username));
        conflicting.remove(uuid);

        if (!conflicting.isEmpty()) {
            conflicting.forEach(this.lookupMap::remove);
            result = result.withOtherUUIDsPresent(conflicting);
        }

        return result;
    }

    public void removeMapping(UUID uuid) { this.lookupMap.remove(uuid); }

    public @Nullable UUID lookupUUID(String username) {
        Set<UUID> uuids = this.lookupMap.lookupUUID(username);
        return Iterables.getFirst(uuids, null);
    }

    public @Nullable String lookupUsername(UUID uuid) { return this.lookupMap.lookupUsername(uuid); }

    public void load(Path file) {
        if (!Files.exists(file)) return;

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String entry;
            while((entry = reader.readLine()) != null) {
                entry = entry.trim();
                if (entry.isEmpty() || entry.startsWith("#")) continue;
                loadEntry(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(Path file) {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write("# UltimateCore UUID lookup cache");
            writer.newLine();
            for (Map.Entry<UUID, String> entry : this.lookupMap.entrySet()) {
                String out = entry.getKey() + ":" + entry.getValue();
                writer.write(out);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEntry(String entry) {
        Iterator<String> parts = KV_SPLIT.split(entry).iterator();

        if (!parts.hasNext()) return;
        String uuidPart = parts.next();

        if (!parts.hasNext()) return;
        String usernamePart = parts.next();

        UUID uuid = Uuids.fromString(uuidPart);
        if (uuid == null) return;

        this.lookupMap.put(uuid, usernamePart);
    }

    private static final class LookupMap extends ConcurrentHashMap<UUID, String> {

        private final SetMultimap<String, UUID> reverse = Multimaps.newSetMultimap(new ConcurrentHashMap<>(), ConcurrentHashMap::newKeySet);

        @Override
        public String put(@NotNull UUID key, @NotNull String value) {
            String existing = super.put(key, value);

            if (!value.equalsIgnoreCase(existing)) {
                if (existing != null) {
                    this.reverse.remove(existing.toLowerCase(Locale.ROOT), key);
                }
            }

            this.reverse.put(value.toLowerCase(Locale.ROOT), key);
            return existing;
        }

        @Override
        public String remove(@NotNull Object k) {
            UUID key = (UUID) k;
            String username = super.remove(key);
            if (username != null) this.reverse.remove(username.toLowerCase(Locale.ROOT), key);
            return username;
        }

        public String lookupUsername(UUID uuid) { return super.get(uuid); }

        public Set<UUID> lookupUUID(String name) { return this.reverse.get(name.toLowerCase(Locale.ROOT)); }

    }

}
