package mc.ultimatecore.collections.serializer.adapters;

import com.google.gson.*;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.objects.PlayerCollections;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class PlayerCollectionsAdapter implements JsonSerializer<PlayerCollections>, JsonDeserializer<PlayerCollections> {
    
    @Override
    public JsonElement serialize(PlayerCollections playerCollections, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            for (String key : HyperCollections.getInstance().getCollections().getCollections().keySet()) {
                object.add(key + "_LEVEL", new JsonPrimitive(playerCollections.getLevel(key)));
                object.add(key + "_XP", new JsonPrimitive(playerCollections.getXP(key)));
                object.add(key + "_RANK", new JsonPrimitive(playerCollections.getRankPosition(key)));
            }
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperCollections.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Skills.");
            return object;
        }
    }
    
    
    @Override
    public PlayerCollections deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {
            PlayerCollections playerSkills = new PlayerCollections();
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