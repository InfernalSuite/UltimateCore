package mc.ultimatecore.skills.serializer.adapters;

import com.google.gson.*;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.abilities.PlayerAbilities;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class AbilitiesAdapter implements JsonSerializer<PlayerAbilities>, JsonDeserializer<PlayerAbilities> {

    @Override
    public JsonElement serialize(PlayerAbilities playerAbilities, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            for(Ability ability : Ability.values())
                object.add(ability.getName(), new JsonPrimitive(playerAbilities.getAbility(ability)));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperSkills.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Abilities.");
            return object;
        }
    }


    @Override
    public PlayerAbilities deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {
            PlayerAbilities playerAbilities = new PlayerAbilities();
            for(Ability ability : Ability.values()) {
                if(object.has(ability.getName()))
                    playerAbilities.setAbility(ability, object.get(ability.getName()).getAsDouble());
            }
            return playerAbilities;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperSkills.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Abilities.");
            return null;
        }
    }
}