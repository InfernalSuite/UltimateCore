package mc.ultimatecore.menu.commands;

import mc.ultimatecore.menu.HyperCore;
import mc.ultimatecore.menu.utils.StringUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super(Collections.singletonList("help"), "Displays the plugin's command", "", true, "/Hypertrades help");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        int page = 1;
        if (args.length == 2) {
            if (!org.apache.commons.lang.StringUtils.isNumeric(args[1])) {
                return;
            }
            page = Integer.parseInt(args[1]);
        }
        int maxpage = (int) Math.ceil(HyperCore.getInstance().getCommandManager().commands.size() / 18.00);
        int current = 0;
        p.sendMessage(StringUtils.color(HyperCore.getInstance().getMessages().getMessage("helpHeader")));
        for (Command command : HyperCore.getInstance().getCommandManager().commands) {
            if ((p.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("nextgenshop.")) && command.isEnabled()) {
                if (current >= (page - 1) * 18 && current < page * 18)
                    p.sendMessage(StringUtils.color(HyperCore.getInstance().getMessages().getMessage("helpMessage").replace("%command%", command.getAliases().get(0)).replace("%description%", command.getDescription())));
                current++;
            }
        }
        BaseComponent[] components = TextComponent.fromLegacyText(StringUtils.color(HyperCore.getInstance().getMessages().getMessage("helpfooter").replace("%maxpage%", maxpage + "").replace("%page%", page + "")));

        for (BaseComponent component : components) {
            if (ChatColor.stripColor(component.toLegacyText()).contains(HyperCore.getInstance().getMessages().getMessage("nextPage"))) {
                if (page < maxpage) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/HyperCore help " + (page + 1)));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(HyperCore.getInstance().getMessages().getMessage("helpPageHoverMessage").replace("%page%", "" + (page + 1))).create()));
                }
            } else if (ChatColor.stripColor(component.toLegacyText()).contains(HyperCore.getInstance().getMessages().getMessage("previousPage"))) {
                if (page > 1) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/HyperCore help " + (page - 1)));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(HyperCore.getInstance().getMessages().getMessage("helpPageHoverMessage").replace("%page%", "" + (page - 1))).create()));
                }
            }
        }
        p.getPlayer().spigot().sendMessage(components);
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }

}
