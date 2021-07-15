package mc.ultimatecore.enchantment.commands;

import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.playerdata.User;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class OpenEnchantmentTable extends Command {

    public OpenEnchantmentTable() {
        super(Collections.singletonList("mainmenu"), "Opens the enchantments main menu", "hyperenchants.mainmenu", true, "/HyperEnchants mainmenu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String world = p.getWorld().getName();
        /*ItemStack itemStack = AEUtils.getEnchantedItem("obsidianshield", XMaterial.DIAMOND_CHESTPLATE.parseItem(), 1);
        ItemMeta meta = itemStack.getItemMeta();
        Bukkit.getConsoleSender().sendMessage("First: "+AEUtils.getItemLevel("obsidianshield", itemStack));

        meta.setLore(Collections.emptyList());
        itemStack.setItemMeta(meta);
        p.getInventory().addItem(itemStack);
        Bukkit.getConsoleSender().sendMessage("Second: "+AEUtils.getItemLevel("obsidianshield", itemStack));
*/
        if(EnchantmentsPlugin.getInstance().getConfiguration().disabledWorlds.contains(world)) return;
        User user = User.getUser(p);
        p.openInventory(user.getMainMenu(Utils.getBookShelfPower(p), true, null).getInventory());
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
