package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.playerdata.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class OpenCraftingTable extends Command {

    private HyperCrafting plugin;

    public OpenCraftingTable(HyperCrafting plugin) {
        super(Collections.singletonList("mainmenu"), "Opens the crafting main menu", "hypercrafting.mainmenu", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = this.plugin.getPlayerManager().createOrGetUser(p.getUniqueId());
        user.getCraftingGUI().openInventory(p);
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
