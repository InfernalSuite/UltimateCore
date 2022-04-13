package mc.ultimatecore.crafting.playerdata;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.crafting.CraftingGUI;
import mc.ultimatecore.crafting.gui.recipeeditor.RecipeCreatorGUI;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class User {

    public String player;
    public String name;
    private transient CraftingGUI craftingGUI;

    public User(OfflinePlayer p) {
        this.player = p.getUniqueId().toString();
        this.name = p.getName();
        HyperCrafting.getInstance().getPlayersData().users.put(this.player, this);
    }

    public static User getUser(String p) {
        if (HyperCrafting.getInstance().getPlayersData().users == null)
            HyperCrafting.getInstance().getPlayersData().users = new HashMap<>();
        return HyperCrafting.getInstance().getPlayersData().users.get(p);
    }

    public static User getUser(OfflinePlayer p) {
        if (p == null) return null;
        if (HyperCrafting.getInstance().getPlayersData().users == null)
            HyperCrafting.getInstance().getPlayersData().users = new HashMap<>();
        return HyperCrafting.getInstance().getPlayersData().users.containsKey(p.getUniqueId().toString()) ? HyperCrafting.getInstance().getPlayersData().users.get(p.getUniqueId().toString()) : new User(p);
    }

    public static boolean userExist(OfflinePlayer p) {
        if (p == null) return false;
        if (HyperCrafting.getInstance().getPlayersData().users == null)
            return false;
        return HyperCrafting.getInstance().getPlayersData().users.containsKey(p.getUniqueId().toString());
    }

    public CraftingGUI getMainMenu(){
        Player player = Bukkit.getPlayer(name);
        CraftingGUI gui = new CraftingGUI(player);
        gui.openInventory(player);

       return gui;
    }

    public RecipeCreatorGUI getRecipeCreatorGUI(CraftingRecipe craftingRecipe){
        Player player = Bukkit.getPlayer(name);
        if(player == null) return null;
        return new RecipeCreatorGUI(craftingRecipe);
    }
}
