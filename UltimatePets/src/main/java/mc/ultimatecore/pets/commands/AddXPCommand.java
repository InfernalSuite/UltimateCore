package mc.ultimatecore.pets.commands;

import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.Tier;
import mc.ultimatecore.pets.playerdata.User;
import mc.ultimatecore.pets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AddXPCommand extends Command {

    public AddXPCommand() {
        super(Collections.singletonList("addxp"), "Add xp to a player's pet.", "pets.addxp", true, "/Pets addxp [Player] [Amount]");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 3){
            try {
                Player player = Bukkit.getPlayer(args[1]);
                int va = Integer.parseInt(args[2]);
                if(player == null){
                    sender.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
                }else{
                    User user = HyperPets.getInstance().getUserManager().getUser(player);
                    if(user.getPlayerPet() == null) return;
                    HyperPets.getInstance().getPetsLeveller().addXP(player, va);
                }
            }catch (Exception e){
                e.printStackTrace();
                sender.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
            }
        } else {
            if(sender instanceof Player)
                sender.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidArguments").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length == 3)
            return new ArrayList<>(HyperPets.getInstance().getPets().pets.keySet());
        if(args.length == 4)
            return HyperPets.getInstance().getTiers().getTierList().values().stream().map(Tier::getName).collect(Collectors.toList());
        return null;
    }
}
