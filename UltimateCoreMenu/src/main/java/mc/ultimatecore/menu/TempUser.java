package mc.ultimatecore.menu;

import mc.ultimatecore.menu.gui.MainMenuGUI;
import org.bukkit.OfflinePlayer;

public class TempUser {
    public String player;

    public String name;

    private MainMenuGUI mainMenuGUI;

    public TempUser(OfflinePlayer p) {
        this.player = p.getUniqueId().toString();
        this.name = p.getName();
        HyperCore.getInstance().getPlayersData().tempUsers.put(p.getUniqueId(), this);
    }

    public static TempUser getUser(OfflinePlayer p) {
        if (p == null)
            return null;
        return HyperCore.getInstance().getPlayersData().tempUsers.containsKey(p.getUniqueId()) ? HyperCore.getInstance().getPlayersData().tempUsers.get(p.getUniqueId()) : new TempUser(p);
    }

    public static boolean userExist(OfflinePlayer p) {
        if (p == null)
            return false;
        return HyperCore.getInstance().getPlayersData().tempUsers.containsKey(p.getUniqueId());
    }

}
