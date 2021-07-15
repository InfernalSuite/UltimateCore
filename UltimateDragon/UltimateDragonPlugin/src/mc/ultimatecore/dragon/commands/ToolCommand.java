package mc.ultimatecore.dragon.commands;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;


public class ToolCommand extends Command {

    public ToolCommand() {
        super(Collections.singletonList("tool"), "Give Tool to remove crystals", "hyperdragon.tool", false, "/HyperDragons tool <player>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 2){
            Player player = Bukkit.getPlayer(args[1]);
            if(player != null) {
                ItemStack item = XMaterial.ARROW.parseItem();
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(StringUtils.color("&aDragon Tool &e(Right-Click)"));
                item.setItemMeta(meta);
                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setBoolean("dragonTool", true);
                player.getInventory().addItem(nbtItem.getItem());
            }else{
                sender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
            }
        }else{
            sender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
