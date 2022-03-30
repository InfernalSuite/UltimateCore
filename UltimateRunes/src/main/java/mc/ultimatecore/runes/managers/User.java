package mc.ultimatecore.runes.managers;

import mc.ultimatecore.runes.HyperRunes;
import mc.ultimatecore.runes.gui.RuneTableGUI;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class User {
  public String player;
  
  public String name;
  

  private transient RuneTableGUI runeTableGUI;
  
  public User(OfflinePlayer p) {
    this.player = p.getUniqueId().toString();
    this.name = p.getName();
    (HyperRunes.getInstance().getRunesManager()).users.put(this.player, this);
  }
  
  public static User getUser(String p) {
    if ((HyperRunes.getInstance().getRunesManager()).users == null)
      (HyperRunes.getInstance().getRunesManager()).users = new HashMap<>();
    return (HyperRunes.getInstance().getRunesManager()).users.get(p);
  }
  
  public static User getUser(OfflinePlayer p) {
    if (p == null)
      return null; 
    if ((HyperRunes.getInstance().getRunesManager()).users == null)
      (HyperRunes.getInstance().getRunesManager()).users = new HashMap<>();
    return (HyperRunes.getInstance().getRunesManager()).users.containsKey(p.getUniqueId().toString()) ? (HyperRunes.getInstance().getRunesManager()).users.get(p.getUniqueId().toString()) : new User(p);
  }
  
  public static boolean userExist(OfflinePlayer p) {
    if (p == null)
      return false; 
    if ((HyperRunes.getInstance().getRunesManager()).users == null)
      return false; 
    return (HyperRunes.getInstance().getRunesManager()).users.containsKey(p.getUniqueId().toString());
  }
  

  public RuneTableGUI getRuneTable() {
    if (this.runeTableGUI == null)
      this.runeTableGUI = new RuneTableGUI(this); 
    return this.runeTableGUI;
  }
}
