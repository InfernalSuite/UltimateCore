package com.infernalsuite.ultimatecore.skills.serializer.adapters;

import com.google.gson.*;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.PlayerSkills;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class SkillsAdapter implements JsonSerializer<PlayerSkills>, JsonDeserializer<PlayerSkills> {

    @Override
    public JsonElement serialize(PlayerSkills playerSkills, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            for(SkillType skillType : SkillType.values()){
                object.add(skillType.getName()+"_LEVEL", new JsonPrimitive(playerSkills.getLevel(skillType)));
                object.add(skillType.getName()+"_XP", new JsonPrimitive(playerSkills.getXP(skillType)));
                object.add(skillType.getName()+"_RANK", new JsonPrimitive(playerSkills.getRankPosition(skillType)));
            }
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperSkills.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Skills.");
            return object;
        }
    }


    @Override
    public PlayerSkills deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {
            PlayerSkills playerSkills = new PlayerSkills();
            for(SkillType skillType : SkillType.values()) {
                if(object.has(skillType.getName() + "_LEVEL"))
                    playerSkills.setLevel(skillType, object.get(skillType.getName() + "_LEVEL").getAsInt());
                if(object.has(skillType.getName() + "_XP"))
                    playerSkills.setXP(skillType, object.get(skillType.getName() + "_XP").getAsDouble());
                if(object.has(skillType.getName() + "_RANK"))
                    playerSkills.setRankPosition(skillType, object.get(skillType.getName() + "_RANK").getAsInt());
            }
            return playerSkills;
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperSkills.getInstance().getLogger().log(Level.WARNING, "Error encountered while deserializing Skills.");
            return null;
        }
    }
}