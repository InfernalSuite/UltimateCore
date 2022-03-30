package mc.ultimatecore.skills.commands;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.gui.MainGUI;
import mc.ultimatecore.skills.utils.StringUtils;
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

    public final List<HyperCommand> commands = new ArrayList<>();

    private final HyperSkills plugin;

    public CommandManager(HyperSkills plugin) {
        this.plugin = plugin;
        HyperSkills.getInstance().getCommand("skills").setExecutor(this);
        HyperSkills.getInstance().getCommand("skills").setTabCompleter(this);
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(new AddAbilitiesCommand());
        registerCommand(new AbilitiesListCommand());
        registerCommand(new AddPerkCommand());
        registerCommand(new GetItemCommand());
        registerCommand(new HelpCommand());
        registerCommand(new MainMenuCommand());
        registerCommand(new PerkListCommand());
        registerCommand(new ProfileGUICommand());
        registerCommand(new ReloadCommand());
        registerCommand(new RemoveAbilitiesCommand());
        registerCommand(new RemovePerkCommand());
        registerCommand(new SetLevelCommand());
        registerCommand(new SetXPCommand());
        registerCommand(new SubMenusCommand());
        registerCommand(new ToolCommand());
        registerCommand(new TopCommand());
        registerCommand(new ItemMenuCommand());
        registerCommand(new NewItemCommand());
        registerCommand(new ResetDataCommand());
        registerCommand(new SilentMoneyCommand());
        registerCommand(new AddXPCommand());
        commands.sort(Comparator.comparing(command -> command.aliases.get(0)));
    }


    public void registerCommand(HyperCommand command) {
        commands.add(command);
    }

    public void unregisterCommand(Command command) {
        commands.remove(command);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof Player) {
                if(HyperSkills.getInstance().getConfiguration().openMenuWithSkills){
                    Player player = (Player) commandSender;
                    player.openInventory(new MainGUI(player.getUniqueId()).getInventory());
                }
                return true;
            }
        }
        for (HyperCommand command : commands) {
            // We don't want to execute other commands or ones that are disabled
            if (command.aliases.size() >= 1 && !(command.aliases.contains(args[0]) && command.enabled)) {
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
                    .equalsIgnoreCase("hyperskills.")) && command.enabled)) {
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
            List<String> result = new ArrayList<>();
            for (HyperCommand command : commands) {
                for (String alias : command.aliases) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (
                            command.enabled && (commandSender.hasPermission(command.permission)
                                    || command.permission.equalsIgnoreCase("") || command.permission
                                    .equalsIgnoreCase("hyperskills.")))) {
                        result.add(alias);
                    }
                }
            }
            return result;
        }

        // Let the sub-command handle the tab completion
        for (HyperCommand command : commands) {
            if (command.aliases.contains(args[0]) && (command.enabled && (
                    commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")
                            || command.permission.equalsIgnoreCase("hyperskills.")))) {
                return command.onTabComplete(commandSender, cmd, label, args);
            }
        }
        return null;
    }

}