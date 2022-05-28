package mc.ultimatecore.collections.serializer.adapters;

import com.google.gson.*;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.objects.PlayerCollection;

import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;

public class PlayerCollectionsAdapter implements JsonSerializer<PlayerCollection>, JsonDeserializer<PlayerCollection> {
    
    @Override
    public JsonElement serialize(PlayerCollection playerCollection, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            for (String key : HyperCollections.getInstance().getCollections().getCollections().keySet()) {
                object.add(key + "_LEVEL", new JsonPrimitive(playerCollection.getLevel(key)));
                object.add(key + "_XP", new JsonPrimitive(playerCollection.getXP(key)));
                object.add(key + "_RANK", new JsonPrimitive(playerCollection.getRankPosition(key)));
            }
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperCollections.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Skills.");
            return object;
        }
    }
    
    
    @Override
    public PlayerCollection deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {
            PlayerCollection playerSkills = new PlayerCollection(UUID.randomUUID());
            for (String key : HyperCollections.getInstance().getCollections().getCollections().keySet()) {
                if (object.has(key + "_LEVEL"))
                    playerSkills.setLevel(key, object.get(key + "_LEVEL").getAsInt());
                if (object.has(key + "_XP"))
                    playerSkills.setXP(key, object.get(key + "_XP").getAsInt());
                if (object.has(key + "_RANK"))
                    playerSkills.setRankPosition(key, object.get(key + "_RANK").getAsInt());
            }
            return playerSkills;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperCollections.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Skills.");
            return null;
        }
    }
}