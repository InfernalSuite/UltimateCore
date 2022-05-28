package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.gui.ArmorEditGUI;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class NewItemCommand extends HyperCommand {

    public NewItemCommand() {
        super(Collections.singletonList("newitem"), "Create new Item", "hyperskills.newitem", true, "/Skills newrecipe <id>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            ItemStack result = p.getItemInHand();
            if (result.getType() != Material.AIR) {
                String name = args[1];
                if (!HyperSkills.getInstance().getUltimateItems().ultimateItems.containsKey(name)){
                    UltimateItem ultimateItem = new UltimateItem(name, result.clone());
                    HyperSkills.getInstance().getUltimateItems().ultimateItems.put(name, ultimateItem);
                    p.openInventory(new ArmorEditGUI(ultimateItem).getInventory());
                }else{
                    p.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("alreadyCreated").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }
            }else{
                p.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("itemInHand").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
            }
        }else{
            p.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }
}
