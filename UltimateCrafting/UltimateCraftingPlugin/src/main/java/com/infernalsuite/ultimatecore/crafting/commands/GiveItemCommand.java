package com.infernalsuite.ultimatecore.crafting.commands;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.crafting.utils.Placeholder;
import com.infernalsuite.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GiveItemCommand extends Command {

    public GiveItemCommand() {
        super(Collections.singletonList("give"), "Give Hypixel Items to a player", "hypercrafting.give", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 4){
            String name = args[1].replace("_", " ");
            String lore = args[2].replace("_", " ");
            XMaterial xMaterial = XMaterial.valueOf(args[3]);
            ItemStack newItem = InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().giveItem,
                    Arrays.asList(new Placeholder("name", name), new Placeholder("lore", lore)), xMaterial.parseMaterial());

            p.getInventory().addItem(newItem);

            ItemMeta meta = newItem.getItemMeta();
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            newItem.setItemMeta(meta);
            p.getInventory().addItem(newItem);

        }else{
            p.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
        }
        return false;
    }

    @Override
    public void admin(CommandSender sender, String[] args) {
        execute(sender, args);
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
