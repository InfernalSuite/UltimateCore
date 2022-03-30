package com.infernalsuite.ultimatecore.enchantment.commands;

import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnchantItemCommand extends Command {

    public EnchantItemCommand() {
        super(Collections.singletonList("enchant"), "To enchant item in your hand", "hyperenchants.enchant", true, "/HyperEnchants enchant <ID> <Level>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 3){
            try {
                ItemStack result = p.getItemInHand();
                if (result != null && result.getType() != Material.AIR) {
                    String name = args[1];
                    int level = Integer.parseInt(args[2]);
                    if (EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.containsKey(name)){
                        HyperEnchant hyperEnchant = EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.get(name);
                        if(!result.getType().toString().contains("BOOK") && !hyperEnchant.itemCanBeEnchanted(result)){
                            p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getConfiguration().prefix+"&7Incompatible enchantment!"));
                            return;
                        }
                        if(level > hyperEnchant.getMaxLevel())
                            p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getConfiguration().prefix+"&7Level can't be higher than enchantment max level!"));
                        else
                            p.getInventory().setItemInHand(EnchantmentsPlugin.getInstance().getApi().enchantItem(result, hyperEnchant, level, false));
                    }else{
                        p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getConfiguration().prefix+"&7Invalid Enchantment!"));
                    }
                }else{
                    p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getConfiguration().prefix+"&7You must have an Item in your hands!"));
                }
            }catch (NumberFormatException e){
                p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getConfiguration().prefix+"&7Invalid Level!"));
            }
        }else{
            p.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", EnchantmentsPlugin.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        if(args.length == 2)
            return new ArrayList<>(EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.keySet());
        return null;
    }

}
