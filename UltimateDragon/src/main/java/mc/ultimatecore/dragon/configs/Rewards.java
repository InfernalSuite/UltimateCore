package mc.ultimatecore.dragon.configs;

import lombok.Getter;
import mc.ultimatecore.dragon.objects.rewards.DragonReward;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Rewards extends YAMLFile {
    private final Set<DragonReward> dragonRewards = new HashSet<>();

    public Rewards(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadRewards();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadRewards();
    }

    private void loadRewards() {
        ConfigurationSection section = getConfig().getConfigurationSection("rewards");
        int loaded = 0;
        if(section == null) return;
        YamlConfiguration cf = getConfig();
        for(String key : section.getKeys(false)) {
            if(!getConfig().getBoolean("rewards." + key + ".enabled")) continue;
            int damageDone = cf.getInt("rewards." + key + ".damageDone");
            List<String> commands = cf.getStringList("rewards." + key + ".commands");
            DragonReward dragonReward = new DragonReward(damageDone, commands);
            dragonRewards.add(dragonReward);
            loaded++;
        }
        if(loaded == 0) return;
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[UltimateCore-Dragon] &aSuccessfully loaded "+loaded+" rewards!"));
    }
}
