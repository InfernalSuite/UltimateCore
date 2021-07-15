package mc.ultimatecore.alchemy.commands;

import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    public List<mc.ultimatecore.alchemy.commands.Command> commands = new ArrayList<>();

    public CommandManager(String command) {
        HyperAlchemy.getInstance().getCommand(command).setExecutor(this);
        HyperAlchemy.getInstance().getCommand(command).setTabCompleter(this);
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(new HelpCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new CreateRecipeCommand());
        registerCommand(new DelRecipeCommand());
        registerCommand(new RecipePreviewCommand());
        registerCommand(new EditorMenuCommand());

    }

    public void registerCommand(mc.ultimatecore.alchemy.commands.Command command) {
        commands.add(command);
    }

    public void unRegisterCommand(mc.ultimatecore.alchemy.commands.Command command) {
        commands.remove(command);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (!HyperAlchemy.getInstance().getConfiguration().mainCommandPerm.equalsIgnoreCase("") && !cs.hasPermission(HyperAlchemy.getInstance().getConfiguration().mainCommandPerm)) {
                cs.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("noPermission").replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
                return false;
            }
            if (args.length != 0) {
                for (mc.ultimatecore.alchemy.commands.Command command : commands) {
                    if (command.getAliases().contains(args[0]) && command.isEnabled()) {
                        if (command.isPlayer() && !(cs instanceof Player)) {
                            // Must be a player
                            //cs.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().mustBeAPlayer.replace("%prefix%", HyperCollections.getInstance().getConfiguration().prefix)));
                            return true;
                        }
                        if ((cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("hyperalchemy.")) && command.isEnabled()) {
                            command.execute(cs, args);
                            Player player = (Player) cs;
                            command.playSound(player);
                        } else {
                            // No permission
                            cs.sendMessage(StringUtils.color(HyperAlchemy.getInstance().getMessages().getMessage("noPermission").replace("%prefix%", HyperAlchemy.getInstance().getConfiguration().prefix)));
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
            HyperAlchemy.getInstance().sendErrorMessage(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (args.length == 1) {
                ArrayList<String> result = new ArrayList<>();
                for (mc.ultimatecore.alchemy.commands.Command command : commands) {
                    for (String alias : command.getAliases()) {
                        if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("hyperalchemy.")))) {
                            result.add(alias);
                        }
                    }
                }
                return result;
            }
            for (mc.ultimatecore.alchemy.commands.Command command : commands) {
                if (command.getAliases().contains(args[0]) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("hyperalchemy.")))) {
                    return command.TabComplete(cs, cmd, s, args);
                }
            }
        } catch (Exception e) {
            HyperAlchemy.getInstance().sendErrorMessage(e);
        }
        return null;
    }
}
