package com.infernalsuite.ultimatecore.skills.implementations;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public interface EconomyPluginImpl {
    double getBalance(OfflinePlayer offlinePlayer);
    double getBalance(UUID uuid);
    void withdraw(OfflinePlayer offlinePlayer, double amount);
    void withdraw(UUID uuid, double amount);
    void deposit(OfflinePlayer offlinePlayer, double amount);
    void deposit(UUID uuid, double amount);
}
