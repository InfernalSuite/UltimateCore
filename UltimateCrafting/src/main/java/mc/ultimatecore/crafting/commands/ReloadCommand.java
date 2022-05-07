package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    private final HyperCrafting plugin;

    public ReloadCommand(HyperCrafting plugin) {
        super(Collections.singletonList("reload"), "Reload all config files", "hypercrafting.reload", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        if (args.length == 1) {
            this.plugin.reloadConfigs();
            p.sendMessage(Utils.color(this.plugin.getMessages().getMessage("reloaded").replace("%prefix%", this.plugin.getConfiguration().prefix)));
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
