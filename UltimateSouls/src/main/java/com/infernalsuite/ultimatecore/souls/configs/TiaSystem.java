package com.infernalsuite.ultimatecore.souls.configs;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.souls.objects.PlayerSouls;
import com.infernalsuite.ultimatecore.souls.objects.Tia;
import com.infernalsuite.ultimatecore.souls.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class TiaSystem extends YAMLFile {
    
    private Tia tia;
    
    public TiaSystem(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }
    
    
    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }
    
    private void loadDefaults() {
        tia = null;
        YamlConfiguration cf = getConfig();
        try {
            List<String> commandRewards = cf.getStringList("tiaSystem.rewards");
            List<String> messages = cf.getStringList("tiaSystem.messages");
            int soulsToClaim = cf.getInt("tiaSystem.soulsToClaim");
            tia = new Tia(soulsToClaim, messages, commandRewards);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("Error Loading Tia System!");
        }
    }
    
    public void claimRewards(Player player) {
        if (hasRequiredSouls(player.getUniqueId())) {
            tia.getMessages().forEach(message -> player.sendMessage(StringUtils.color(message.replace("%player%", player.getName()))));
            tia.getRewards().forEach(reward -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward.replace("%player%", player.getName())));
            HyperSouls.getInstance().getDatabaseManager().getSoulsData(player).addSoulsExchanged(tia.getSoulsToClaim());
        } else {
            player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("notEnoughSouls")));
        }
    }
    
    public boolean hasRequiredSouls(UUID uuid) {
        int claimedSouls = getToExchange(uuid);
        return claimedSouls >= getRequired();
    }
    
    public int getToExchange(UUID uuid) {
        PlayerSouls playerSouls = HyperSouls.getInstance().getDatabaseManager().getSoulsData(uuid);
        return playerSouls.getAmount() - playerSouls.getSoulsExchanged();
    }
    
    public int getRequired() {
        return tia.getSoulsToClaim();
    }
    
}
