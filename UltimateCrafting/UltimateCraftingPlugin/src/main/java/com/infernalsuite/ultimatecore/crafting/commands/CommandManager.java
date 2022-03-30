package com.infernalsuite.ultimatecore.crafting.commands;

import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    public List<mc.ultimatecore.crafting.commands.Command> commands = new ArrayList<>();

    public CommandManager(String command) {
        HyperCrafting.getInstance().getCommand(command).setExecutor(this);
        HyperCrafting.getInstance().getCommand(command).setTabCompleter(this);
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(new OpenCraftingTable());
        registerCommand(new HelpCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new CreateRecipeCommand());
        registerCommand(new DelRecipeCommand());
        registerCommand(new RecipePreviewCommand());
        registerCommand(new EditorMenuCommand());
        registerCommand(new RecipeBookCommand());
        registerCommand(new CategoryGUICommand());
        registerCommand(new GiveItemCommand());

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
            if (!HyperCrafting.getInstance().getConfiguration().mainCommandPerm.equalsIgnoreCase("") && !cs.hasPermission(HyperCrafting.getInstance().getConfiguration().mainCommandPerm)) {
                cs.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("noPermission").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
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
                            cs.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("noPermission").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
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
            HyperCrafting.getInstance().sendErrorMessage(e);
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
            HyperCrafting.getInstance().sendErrorMessage(e);
        }
        return null;
    }
}
