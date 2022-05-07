package mc.ultimatecore.crafting.commands;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private HyperCrafting plugin;

    public List<mc.ultimatecore.crafting.commands.Command> commands = new ArrayList<>();

    public CommandManager(String command, HyperCrafting plugin) {
        // REGISTER PLUGIN INSTANCE
        this.plugin = plugin;

        // REGISTER SELF COMMAND
        if(plugin.getCommand(command) != null) {
            plugin.getCommand(command).setExecutor(this);
            plugin.getCommand(command).setTabCompleter(this);
        }

        // REGISTER COMMANDS
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(new OpenCraftingTable(this.plugin));
        registerCommand(new HelpCommand(this.plugin));
        registerCommand(new ReloadCommand(this.plugin));
        registerCommand(new CreateRecipeCommand(this.plugin));
        registerCommand(new DelRecipeCommand(this.plugin));
        registerCommand(new RecipePreviewCommand(this.plugin));
        registerCommand(new EditorMenuCommand(this.plugin));
        registerCommand(new RecipeBookCommand(this.plugin));
        registerCommand(new CategoryGUICommand(this.plugin));
        registerCommand(new GiveItemCommand(this.plugin));

    }

    public void registerCommand(mc.ultimatecore.crafting.commands.Command command) {
        commands.add(command);
    }

    public void unRegisterCommand(mc.ultimatecore.crafting.commands.Command command) {
        commands.remove(command);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (!this.plugin.getConfiguration().mainCommandPerm.equalsIgnoreCase("") && !cs.hasPermission(this.plugin.getConfiguration().mainCommandPerm)) {
                cs.sendMessage(Utils.color(this.plugin.getMessages().getMessage("noPermission").replace("%prefix%", this.plugin.getConfiguration().prefix)));
                return false;
            }
            if (args.length != 0) {
                for (mc.ultimatecore.crafting.commands.Command command : commands) {
                    if (command.getAliases().contains(args[0]) && command.isEnabled()) {
                        if (command.isPlayer() && !(cs instanceof Player)) {
                            // Must be a player
                            //cs.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().mustBeAPlayer.replace("%prefix%", HyperCollections.getInstance().getConfiguration().prefix)));
                            return true;
                        }
                        if ((cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("hypercrafting.")) && command.isEnabled()) {
                            command.execute(cs, args);
                            Player player = (Player) cs;
                            command.playSound();
                        } else {
                            // No permission
                            cs.sendMessage(Utils.color(this.plugin.getMessages().getMessage("noPermission").replace("%prefix%", this.plugin.getConfiguration().prefix)));
                        }
                        return true;
                    }
                }
            } else {
                if (cs instanceof Player) {

                    return true;
                }
            }
            //cs.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().unknownCommand.replace("%prefix%", HyperCollections.getInstance().getConfiguration().prefix)));
        } catch (Exception e) {
            this.plugin.sendErrorMessage(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (args.length == 1) {
                ArrayList<String> result = new ArrayList<>();
                for (mc.ultimatecore.crafting.commands.Command command : commands) {
                    for (String alias : command.getAliases()) {
                        if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("hypercrafting.")))) {
                            result.add(alias);
                        }
                    }
                }
                return result;
            }
            for (mc.ultimatecore.crafting.commands.Command command : commands) {
                if (command.getAliases().contains(args[0]) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("hypercrafting.")))) {
                    return command.TabComplete(cs, cmd, s, args);
                }
            }
        } catch (Exception e) {
            this.plugin.sendErrorMessage(e);
        }
        return null;
    }
}
