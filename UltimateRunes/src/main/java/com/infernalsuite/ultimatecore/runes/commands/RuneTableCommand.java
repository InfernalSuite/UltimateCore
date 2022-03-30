package com.infernalsuite.ultimatecore.runes.commands;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.runes.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.runes.utils.StringUtils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.runes.HyperRunes;
import com.infernalsuite.ultimatecore.runes.Item;
import com.infernalsuite.ultimatecore.runes.managers.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RuneTableCommand extends Command {

    public RuneTableCommand() {
        super(Collections.singletonList("runetable"), "Open and get runetable", "hyperrunes.runetable", true, "/Hyperrunes runetable [open|get]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if(args.length == 2){
            if(args[1].equalsIgnoreCase("open")){
                if(p.hasPermission("hyperrunes.runetable.open")) {
                    p.openInventory(user.getRuneTable().getInventory());
                }else{
                    p.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("noPermission").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
                }
            }else if(args[1].equalsIgnoreCase("get")){
                if(p.hasPermission("hyperrunes.runetable.get")){
                    Item item = new Item(XMaterial.PLAYER_HEAD, 4, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM1NDAyOThhMDE3YjI1ZjljZmFlOTI4MWZlNWI1ODVkNzcwZGIxODUyYjczODA0ZDFiYjdjN2VlNTM3MzNhNCJ9fX0=", 1, "&aRuneTable &e(Click to place)", Collections.singletonList(""));
                    NBTItem nbtItem = new NBTItem(InventoryUtils.makeItem(item));
                    nbtItem.setBoolean("runeStructure", true);
                    p.getInventory().addItem(nbtItem.getItem());
                }else{
                    p.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("noPermission").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
                }
            }else{
                p.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("invalidCommand").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
            }
        }else{
            p.sendMessage(StringUtils.color(HyperRunes.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperRunes.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
