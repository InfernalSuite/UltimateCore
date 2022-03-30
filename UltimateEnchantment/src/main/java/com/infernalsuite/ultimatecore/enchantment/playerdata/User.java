package com.infernalsuite.ultimatecore.enchantment.playerdata;

import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.gui.EnchantingGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class User {

    public String player;
    public String name;
    private EnchantingGUI enchantingGUI;

    public User(OfflinePlayer p) {
        this.player = p.getUniqueId().toString();
        this.name = p.getName();
        EnchantmentsPlugin.getInstance().getPlayersData().users.put(this.player, this);
    }

    public static User getUser(OfflinePlayer p) {
        if (p == null) return null;
        if (EnchantmentsPlugin.getInstance().getPlayersData().users == null)
            EnchantmentsPlugin.getInstance().getPlayersData().users = new HashMap<>();
        return EnchantmentsPlugin.getInstance().getPlayersData().users.containsKey(p.getUniqueId().toString()) ? EnchantmentsPlugin.getInstance().getPlayersData().users.get(p.getUniqueId().toString()) : new User(p);
    }

    public EnchantingGUI getMainMenu(int bookShelfPower, boolean createNew, ItemStack itemStack){
        Player player = Bukkit.getPlayer(name);
        if(player == null) return null;
        if(enchantingGUI == null || createNew)
            enchantingGUI = new EnchantingGUI(bookShelfPower, itemStack);
        return enchantingGUI;
    }

}
