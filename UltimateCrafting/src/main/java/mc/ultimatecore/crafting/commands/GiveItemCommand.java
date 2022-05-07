package mc.ultimatecore.crafting.commands;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Placeholder;
import mc.ultimatecore.crafting.utils.Utils;
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

    private final HyperCrafting plugin;

    public GiveItemCommand(HyperCrafting plugin) {
        super(Collections.singletonList("give"), "Give Hypixel Items to a player", "hypercrafting.give", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 4){
            String name = args[1].replace("_", " ");
            String lore = args[2].replace("_", " ");
            XMaterial xMaterial = XMaterial.valueOf(args[3]);
            ItemStack newItem = InventoryUtils.makeItem(this.plugin.getInventories().giveItem,
                    Arrays.asList(new Placeholder("name", name), new Placeholder("lore", lore)), xMaterial.parseMaterial());

            p.getInventory().addItem(newItem);

            ItemMeta meta = newItem.getItemMeta();
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            newItem.setItemMeta(meta);
            p.getInventory().addItem(newItem);

        }else{
            p.sendMessage(Utils.color(this.plugin.getMessages().getMessage("invalidArguments").replace("%prefix%", this.plugin.getConfiguration().prefix)));
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
