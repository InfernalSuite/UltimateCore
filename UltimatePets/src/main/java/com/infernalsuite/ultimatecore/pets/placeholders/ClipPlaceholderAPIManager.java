package com.infernalsuite.ultimatecore.pets.placeholders;

import com.infernalsuite.ultimatecore.pets.HyperPets;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ClipPlaceholderAPIManager extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "pets";
    }

    @Override
    public String getAuthor() {
        return "UltimateCore";
    }

    @Override
    public String getVersion() {
        return HyperPets.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String placeholder) {
        if (player == null || placeholder == null) {
            return "";
        }
        switch (placeholder) {

        }
        return null;
    }
}