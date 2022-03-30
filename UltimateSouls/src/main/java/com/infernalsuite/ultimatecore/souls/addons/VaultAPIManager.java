package com.infernalsuite.ultimatecore.souls.addons;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class VaultAPIManager {
    
    private Economy economy;
    
    public VaultAPIManager() {
        RegisteredServiceProvider<Economy> rsp = HyperSouls.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return;
        economy = rsp.getProvider();
    }
    
    public double getBalance(OfflinePlayer offlinePlayer) {
        return economy.getBalance(offlinePlayer);
    }
    
    public double getBalance(UUID uuid) {
        return getBalance(Bukkit.getOfflinePlayer(uuid));
    }
    
    public void withdraw(OfflinePlayer offlinePlayer, double amount) {
        economy.withdrawPlayer(offlinePlayer, amount);
    }
    
    public void withdraw(UUID uuid, double amount) {
        withdraw(Bukkit.getOfflinePlayer(uuid), amount);
    }
    
    public void deposit(OfflinePlayer offlinePlayer, double amount) {
        economy.depositPlayer(offlinePlayer, amount);
    }
    
    public void deposit(UUID uuid, double amount) {
        deposit(Bukkit.getOfflinePlayer(uuid), amount);
    }
}
