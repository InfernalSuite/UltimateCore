package mc.ultimatecore.anvil.commands;

import mc.ultimatecore.anvil.HyperAnvil;
import net.md_5.bungee.api.chat.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super(Collections.singletonList("help"), "Displays the plugin's command", "", true, "/Hyperrunes help");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        int page = 1;
        if (args.length == 2) {
            if (!StringUtils.isNumeric(args[1])) {
                return;
            }
            page = Integer.parseInt(args[1]);
        }
        int maxpage = (int) Math.ceil(HyperAnvil.getInstance().getCommandManager().commands.size() / 18.00);
        int current = 0;
        p.sendMessage(mc.ultimatecore.anvil.utils.StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("helpHeader")));
        for (Command command : HyperAnvil.getInstance().getCommandManager().commands) {
            if ((p.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("hyperanvil.")) && command.isEnabled()) {
                if (current >= (page - 1) * 18 && current < page * 18)
                    p.sendMessage(mc.ultimatecore.anvil.utils.StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("helpMessage").replace("%command%", command.getUsage()).replace("%description%", command.getDescription())));
                current++;
            }
        }
        BaseComponent[] components = TextComponent.fromLegacyText(mc.ultimatecore.anvil.utils.StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("helpfooter").replace("%maxpage%", maxpage + "").replace("%page%", page + "")));

        for (BaseComponent component : components) {
            if (ChatColor.stripColor(component.toLegacyText()).contains(HyperAnvil.getInstance().getMessages().getMessage("nextPage"))) {
                if (page < maxpage) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/skills help " + (page + 1)));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(HyperAnvil.getInstance().getMessages().getMessage("helpPageHoverMessage").replace("%page%", "" + (page + 1))).create()));
                }
            } else if (ChatColor.stripColor(component.toLegacyText()).contains(HyperAnvil.getInstance().getMessages().getMessage("previousPage"))) {
                if (page > 1) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/skills help " + (page - 1)));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(HyperAnvil.getInstance().getMessages().getMessage("helpPageHoverMessage").replace("%page%", "" + (page - 1))).create()));
                }
            }
        }
        p.getPlayer().spigot().sendMessage(components);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return null;
    }

}
