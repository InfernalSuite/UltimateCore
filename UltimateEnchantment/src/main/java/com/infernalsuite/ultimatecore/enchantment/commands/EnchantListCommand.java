package com.infernalsuite.ultimatecore.enchantment.commands;

import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class EnchantListCommand extends Command {

    public EnchantListCommand() {
        super(Collections.singletonList("list"), "Shows enchantments list", "hyperenchants.list", true, "/HyperEnchants list");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(Utils.color("&6&lEnchantments List"));
        for(HyperEnchant hyperEnchant : EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.values()){
            p.sendMessage(Utils.color("&6• &eID: "+hyperEnchant.getEnchantmentName()));
        }
        p.sendMessage(Utils.color("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
