package mc.ultimatecore.talismans.objects.implementations;

import lombok.Getter;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.talismans.objects.Talisman;
import mc.ultimatecore.talismans.objects.TalismanType;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class AbilityTalisman extends Talisman implements StatsTalisman{
    @Getter
    protected final Map<Ability, Double> abilities;

    public AbilityTalisman(String name, TalismanType talismanType, String displayName, List<String> lore, String texture, Map<Ability, Double> abilities, boolean executable) {
        super(name, talismanType, displayName, lore, texture, executable);
        this.abilities = abilities;
    }

    @Override
    public void execute(Player player, Integer repeat) {
        abilities.forEach((ability, amount) -> HyperSkills.getInstance().getApi().addArmorAbility(player.getUniqueId(), ability, amount * repeat));
    }

    @Override
    public void stop(Player player, Integer repeat) {
        abilities.forEach((ability, amount) -> HyperSkills.getInstance().getApi().removeArmorAbility(player.getUniqueId(), ability, amount * repeat));
    }
}
