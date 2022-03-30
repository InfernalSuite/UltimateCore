package com.infernalsuite.ultimatecore.talismans.objects.implementations;

import com.infernalsuite.ultimatecore.talismans.objects.Talisman;
import com.infernalsuite.ultimatecore.talismans.objects.TalismanType;
import lombok.Getter;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class PerkTalisman extends Talisman implements StatsTalisman{
    @Getter
    protected final Map<Perk, Double> perks;

    public PerkTalisman(String name, TalismanType talismanType, String displayName, List<String> lore, String texture, Map<Perk, Double> perks, boolean executable) {
        super(name, talismanType, displayName, lore, texture, executable);
        this.perks = perks;
    }

    @Override
    public void execute(Player player, Integer repeat) {
        perks.forEach((perk, amount) -> HyperSkills.getInstance().getApi().addArmorPerk(player.getUniqueId(), perk, amount * repeat));
    }

    @Override
    public void stop(Player player, Integer repeat) {
        perks.forEach((perk, amount) -> HyperSkills.getInstance().getApi().removeArmorPerk(player.getUniqueId(), perk, amount * repeat));
    }
}
