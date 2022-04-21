package mc.ultimatecore.farm.commands;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    public List<mc.ultimatecore.farm.commands.Command> commands = new ArrayList<>();

    public CommandManager(String command) {
        if(HyperRegions.getInstance().getCommand(command) != null) {
            HyperRegions.getInstance().getCommand(command).setExecutor(this);
            HyperRegions.getInstance().getCommand(command).setTabCompleter(this);
        }
    }

    public void registerCommands() {
        registerCommand(new MainMenuCommand());
        registerCommand(new HelpCommand());
        registerCommand(new RegionListCommand());
        registerCommand(new TypeListCommand());
        registerCommand(new AddGuardianCommand());
        registerCommand(new RemoveGuardianCommand());
        registerCommand(new ReloadCommand());
    }

    public void registerCommand(mc.ultimatecore.farm.commands.Command command) {
        commands.add(command);
    }

    public void unRegisterCommand(mc.ultimatecore.farm.commands.Command command) {
        commands.remove(command);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (!cs.hasPermission("")) {
                cs.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().noPermission.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
                return false;
            }
            if (args.length != 0) {
                for (mc.ultimatecore.farm.commands.Command command : commands) {
                    if (command.getAliases().contains(args[0]) && command.isEnabled()) {
                        if (command.isPlayer() && !(cs instanceof Player)) {
                            // Must be a player
                            cs.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().mustBeAPlayer.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
                            return true;
                        }
                        if ((cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("ultimatefarm.")) && command.isEnabled()) {
                            command.execute(cs, args);
                            Player player = (Player) cs;
                            command.playSound(player);
                        } else {
                            // No permission
                            cs.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().noPermission.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
                        }
                        return true;
                    }
                }
            } else {
                if (cs instanceof Player) {
                    return true;
                }
            }
            cs.sendMessage(Utils.color(HyperRegions.getInstance().getMessages().unknownCommand.replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
        } catch (Exception e) {
            HyperRegions.getInstance().sendErrorMessage(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (args.length == 1) {
                ArrayList<String> result = new ArrayList<>();
                for (mc.ultimatecore.farm.commands.Command command : commands) {
                    for (String alias : command.getAliases()) {
                        if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("ultimatefarm.")))) {
                            result.add(alias);
                        }
                    }
                }
                return result;
            }
            for (mc.ultimatecore.farm.commands.Command command : commands) {
                if (command.getAliases().contains(args[0]) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("ultimatefarm.")))) {
                    return command.TabComplete(cs, cmd, s, args);
                }
            }
        } catch (Exception e) {
            HyperRegions.getInstance().sendErrorMessage(e);
        }
        return null;
    }
}
