package mc.ultimatecore.dragon.commands;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class EditModeCommand extends Command {

    public EditModeCommand() {
        super(Collections.singletonList("editormode"), "Use or leave Editor mode", "hyperdragon.editmode", true, "/HyperDragons editormode");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 1){
            UUID uuid = p.getUniqueId();
            Set<UUID> editorMode = HyperDragons.getInstance().getDragonManager().getEditorMode();
            if(editorMode.contains(uuid)){
                editorMode.remove(uuid);
                p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("editModeLeave").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
            }else{
                editorMode.add(uuid);
                HyperDragons.getInstance().getMessages().getEditorModeJoinMsg().forEach(message -> p.sendMessage(StringUtils.color(message.replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix))));
            }
        }else{
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }

}
