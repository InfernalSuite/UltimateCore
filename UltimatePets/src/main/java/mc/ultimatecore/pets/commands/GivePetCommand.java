package mc.ultimatecore.pets.commands;

import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.Pet;
import mc.ultimatecore.pets.objects.Tier;
import mc.ultimatecore.pets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GivePetCommand extends Command {

    public GivePetCommand() {
        super(Collections.singletonList("give"), "Give specials items", "pets.give", false, "/Pets give [Player] [Pet] [Tier]");
    }

    /*
    /pets getitem <petname> <tier> <player>
     */

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 4){
            try {
                Player player = Bukkit.getPlayer(args[1]);
                String name = args[2];
                Tier tier = HyperPets.getInstance().getTiers().getTier(args[3]);
                if(tier == null){
                    sender.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidTier").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
                    return;
                }
                Pet pet = HyperPets.getInstance().getPets().getPetByID(name);
                if(pet == null){
                    if(sender instanceof Player)
                        sender.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidPet").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
                }else{
                    if (player != null) {
                        Bukkit.getServer().getScheduler().runTaskAsynchronously(HyperPets.getInstance(), () -> {
                            List<Integer> allPets = HyperPets.getInstance().getPluginDatabase().getPetsID();
                            int petUUID = allPets.size() == 0 ? 1 : allPets.get(allPets.size() - 1) + 1;
                            Bukkit.getServer().getScheduler().runTask(HyperPets.getInstance(), () -> player.getInventory().addItem(HyperPets.getInstance().getPets().getPetItem(name, petUUID, tier)));
                        });
                    } else{
                        if(sender instanceof Player)
                            sender.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidPlayer").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                sender.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("invalidTier").replace("%prefix%", HyperPets.getInstance().getConfiguration().prefix)));
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
