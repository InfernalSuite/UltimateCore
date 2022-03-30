package com.infernalsuite.ultimatecore.skills.utils;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import org.bukkit.entity.Player;

public class AttributeUtils {
    private static final boolean SUPPORT;

    static {
        boolean support = true;
        try {
            Class.forName("org.bukkit.attribute.AttributeModifier");
        } catch (ClassNotFoundException e) {
            support = false;
        }
        SUPPORT = support;
    }

    public static void manageAttribute(Player player, double amount, HyperSkills plugin) {
        if (SUPPORT) {
            org.bukkit.attribute.AttributeInstance attributeModifiers = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH);
            if (attributeModifiers != null && !plugin.getAddonsManager().isMMOItems())
                attributeModifiers.setBaseValue(amount);
        } else {
            player.setMaxHealth(amount);
        }
    }
}