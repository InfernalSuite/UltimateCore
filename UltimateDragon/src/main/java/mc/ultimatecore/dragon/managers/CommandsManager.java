package mc.ultimatecore.dragon.managers;


import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.commands.*;
import mc.ultimatecore.dragon.implementations.CoreManagerImpl;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommandsManager extends CoreManagerImpl implements CommandExecutor, TabCompleter {

    public final List<mc.ultimatecore.dragon.commands.Command> commands = new ArrayList<>();


    public CommandsManager(HyperDragons plugin) {
        super(plugin);
        plugin.getCommand("hyperdragon").setExecutor(this);
        plugin.getCommand("hyperdragon").setTabCompleter(this);
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(new SetSpawnCommand());
        registerCommand(new MenuCommand());
        registerCommand(new EditModeCommand());
        registerCommand(new AddCrystalCommand());
        registerCommand(new GiveEnderEyeCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new ToolCommand());
        registerCommand(new KillDragonCommand());
        registerCommand(new GuardianCommand());
        registerCommand(new AddGuardianCommand());
        registerCommand(new SpawnGuardianCommand());

        commands.sort(Comparator.comparing(command -> command.aliases.get(0)));
    }


    public void registerCommand(mc.ultimatecore.dragon.commands.Command command) {
        commands.add(command);
    }


    public void unregisterCommand(mc.ultimatecore.dragon.commands.Command command) {
        commands.remove(command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof Player) {
                return true;
            }
        }

        for (mc.ultimatecore.dragon.commands.Command command : commands) {
            // We don't want to execute other commands or ones that are disabled
            if (!(command.aliases.contains(args[0]) && command.enabled)) {
                continue;
            }

            if (command.onlyForPlayers && !(commandSender instanceof Player)) {
                // Must be a player
                commandSender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("mustBeAPlayer")
                        .replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
                return false;
            }

            if (!((commandSender.hasPermission(command.permission) || command.permission
                    .equalsIgnoreCase("") || command.permission
                    .equalsIgnoreCase("hyperdragon.")) && command.enabled)) {
                // No permissions
                commandSender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("noPermission")
                        .replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
                return false;
            }

            command.execute(commandSender, args);
            return true;
        }
        // Unknown commands message
        commandSender.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("unknownCommand")
                .replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command cmd, String label, String[] args) {
        // Handle the tab completion if it's a sub-commands.
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (mc.ultimatecore.dragon.commands.Command command : commands) {
                for (String alias : command.aliases) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (
                            command.enabled && (commandSender.hasPermission(command.permission)
                                    || command.permission.equalsIgnoreCase("") || command.permission
                                    .equalsIgnoreCase("hyperdragon.")))) {
                        result.add(alias);
                    }
                }
            }
            return result;
        }

        // Let the sub-commands handle the tab completion
        for (mc.ultimatecore.dragon.commands.Command command : commands) {
            if (command.aliases.contains(args[0]) && (command.enabled && (
                    commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")
                            || command.permission.equalsIgnoreCase("hyperdragon.")))) {
                return command.onTabComplete(commandSender, cmd, label, args);
            }
        }

        return null;
    }
}
