package com.infernalsuite.ultimatecore.anvil.commands;

import com.infernalsuite.ultimatecore.anvil.HyperAnvil;
import com.infernalsuite.ultimatecore.anvil.utils.StringUtils;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveBookItem extends Command {
    
    public GiveBookItem() {
        super(Collections.singletonList("givebook"), "To Give Enchanted Book", "hyperanvil.givebook", true, "/HyperAnvil givebook <player> <enchantment> <level>");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 4) {
            try {
                String name = args[2];
                int level = Integer.parseInt(args[3]);
                HyperEnchant hyperEnchant = EnchantmentsPlugin.getInstance().getApi().getEnchantmentInstance(name);
                if (hyperEnchant != null) {
                    if (level > hyperEnchant.getMaxLevel())
                        p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getConfiguration().prefix + "&7Level can't be higher than enchantment max level!"));
                    else
                        p.getInventory().addItem(EnchantmentsPlugin.getInstance().getApi().getEnchantedBook(hyperEnchant, level));
                } else {
                    p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getConfiguration().prefix + "&7Invalid Enchantment!"));
                }
            } catch (Exception e) {
                p.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperAnvil.getInstance().getConfiguration().prefix)));
            }
        } else {
            p.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperAnvil.getInstance().getConfiguration().prefix)));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 3)
            return new ArrayList<>(EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.keySet());
        return null;
    }
    
}
