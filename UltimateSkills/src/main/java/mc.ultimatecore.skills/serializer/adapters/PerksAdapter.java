package mc.ultimatecore.skills.serializer.adapters;

import com.google.gson.*;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.objects.perks.PlayerPerks;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class PerksAdapter implements JsonSerializer<PlayerPerks>, JsonDeserializer<PlayerPerks> {

    @Override
    public JsonElement serialize(PlayerPerks playerPerks, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            for(Perk perk : Perk.values())
                object.add(perk.getName(), new JsonPrimitive(playerPerks.getPerk(perk)));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperSkills.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Perks.");
            return object;
        }
    }

    @Override
    public PlayerPerks deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {
            PlayerPerks playerPerks = new PlayerPerks();
            for(Perk perk : Perk.values()) {
                if(object.has(perk.getName()))
                    playerPerks.setPerk(perk, object.get(perk.getName()).getAsDouble());
            }
            return playerPerks;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperSkills.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Perks.");
            return null;
        }
    }
}