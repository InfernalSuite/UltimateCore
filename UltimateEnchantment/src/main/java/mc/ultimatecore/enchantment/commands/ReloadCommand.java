package mc.ultimatecore.enchantment.commands;

import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload all config files", "", false, "/HyperEnchants reload");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 1) {
            EnchantmentsPlugin.getInstance().reloadConfigs();
            cs.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getMessages().getMessage("reloaded").replace("%prefix%", EnchantmentsPlugin.getInstance().getConfiguration().prefix)));
        }else{
            cs.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", EnchantmentsPlugin.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
