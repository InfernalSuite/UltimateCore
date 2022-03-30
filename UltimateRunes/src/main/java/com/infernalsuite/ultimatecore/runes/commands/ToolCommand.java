package com.infernalsuite.ultimatecore.runes.commands;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.runes.utils.StringUtils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class ToolCommand extends Command {

    public ToolCommand() {
        super(Collections.singletonList("removetool"), "Get a tool to remove invincible armorstands", "hyperrunes.removetool", true, "/Skills removetool");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        ItemStack item = XMaterial.ARROW.parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(StringUtils.color("&aRemover Tool &e(Right-Click)"));
        item.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("runesRemove", true);
        p.getInventory().addItem(nbtItem.getItem());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
