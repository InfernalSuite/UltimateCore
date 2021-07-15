package mc.ultimatecore.skills.addons;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.api.HyperSkillsAPI;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ClipPlaceholderAPIManager extends PlaceholderExpansion {

    private final HyperSkills plugin;

    public ClipPlaceholderAPIManager(HyperSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @NotNull
    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }


    @NotNull
    @Override
    public String getIdentifier() {
        return "skills";
    }

    @NotNull
    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null || identifier == null) {
            return "";
        }
        HyperSkillsAPI api = HyperSkills.getInstance().getApi();
        UUID uuid = player.getUniqueId();
        if(identifier.equals("current_health"))
            return Utils.getHealth(player) + "";
        for(SkillType skillType : SkillType.values()){
            if(identifier.equals(skillType.getLowName()+"_level"))
                return api.getLevel(uuid, skillType) + "";
            if(identifier.equals(skillType.getLowName()+"_level_roman"))
                return Utils.toRoman(api.getLevel(uuid, skillType)) + "";
            if(identifier.equals(skillType.getLowName()+"_xp"))
                return api.getXP(uuid, skillType) + "";
            if(identifier.equals(skillType.getLowName()+"_rank"))
                return api.getRank(uuid, skillType) + "";
            if(identifier.equals(skillType.getLowName()+"_progressbar"))
                return Utils.getProgressBar(uuid, skillType);
            if(identifier.equals(skillType.getLowName()+"_xp_left"))
                return (plugin.getRequirements().getLevelRequirement(skillType, api.getLevel(uuid, skillType)) - api.getXP(uuid, skillType)) + "";
        }
        for(Ability ability : Ability.values()){
            if(identifier.equals(ability.getLowName()+"_total"))
                return api.getTotalAbility(player.getUniqueId(), ability) + "";
            if(identifier.equals(ability.getLowName()+"_extra"))
                return api.getExtraAbility(player.getUniqueId(), ability) + "";
            if(identifier.equals(ability.getLowName()+"_simple"))
                return api.getSimpleAbility(player.getUniqueId(), ability) + "";
        }
        for(Perk perk : Perk.values()){
            if(identifier.equals(perk.getLowName()+"_total"))
                return api.getTotalPerk(player.getUniqueId(), perk) + "";
            if(identifier.equals(perk.getLowName()+"_extra"))
                return api.getExtraPerk(player.getUniqueId(), perk) + "";
            if(identifier.equals(perk.getLowName()+"_simple"))
                return api.getSimplePerk(player.getUniqueId(), perk) + "";
        }
        return null;
    }
}