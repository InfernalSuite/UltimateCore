package mc.ultimatecore.souls.commands;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    
    public final List<mc.ultimatecore.souls.commands.Command> commands = new ArrayList<>();
    private final HyperSouls plugin;
    
    public CommandManager(HyperSouls plugin) {
        this.plugin = plugin;
        plugin.getCommand("souls").setExecutor(this);
        plugin.getCommand("souls").setTabCompleter(this);
        registerCommands();
    }
    
    public void registerCommands() {
        registerCommand(new HelpCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new SoulRemoveCommand());
        registerCommand(new SoulsGetCommand());
        registerCommand(new SoulsMenuCommand());
        registerCommand(new TiaMenuCommand());
        registerCommand(new SoulsResetCommand());
        
        commands.sort(Comparator.comparing(command -> command.aliases.get(0)));
    }
    
    
    public void registerCommand(mc.ultimatecore.souls.commands.Command command) {
        commands.add(command);
    }
    
    public void unregisterCommand(Command command) {
        commands.remove(command);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof Player) {
                return true;
            }
        }
        
        for (mc.ultimatecore.souls.commands.Command command : commands) {
            // We don't want to execute other commands or ones that are disabled
            if (!(command.aliases.contains(args[0]) && command.enabled)) {
                continue;
            }
            
            if (command.onlyForPlayers && !(commandSender instanceof Player)) {
                // Must be a player
                commandSender.sendMessage(StringUtils.color(plugin.getMessages().getMessage("mustBeAPlayer")
                                                                  .replace("%prefix%", plugin.getConfiguration().prefix)));
                return false;
            }
            
            if (!((commandSender.hasPermission(command.permission) || command.permission
                    .equalsIgnoreCase("") || command.permission
                    .equalsIgnoreCase("hypersouls.")) && command.enabled)) {
                // No permissions
                commandSender.sendMessage(StringUtils.color(plugin.getMessages().getMessage("noPermission")
                                                                  .replace("%prefix%", plugin.getConfiguration().prefix)));
                return false;
            }
            
            command.execute(commandSender, args);
            return true;
        }
        // Unknown command message
        commandSender.sendMessage(StringUtils.color(plugin.getMessages().getMessage("unknownCommand")
                                                          .replace("%prefix%", plugin.getConfiguration().prefix)));
        return false;
    }
    
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String label, String[] args) {
        // Handle the tab completion if it's a sub-command.
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (mc.ultimatecore.souls.commands.Command command : commands) {
                for (String alias : command.aliases) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (
                            command.enabled && (commandSender.hasPermission(command.permission)
                                    || command.permission.equalsIgnoreCase("") || command.permission
                                    .equalsIgnoreCase("hypersouls.")))) {
                        result.add(alias);
                    }
                }
            }
            return result;
        }
        
        // Let the sub-command handle the tab completion
        for (mc.ultimatecore.souls.commands.Command command : commands) {
            if (command.aliases.contains(args[0]) && (command.enabled && (
                    commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")
                            || command.permission.equalsIgnoreCase("hypersouls.")))) {
                return command.onTabComplete(commandSender, cmd, label, args);
            }
        }
        
        return null;
    }
    
}