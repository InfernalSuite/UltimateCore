package com.infernalsuite.ultimatecore.reforge;

import com.infernalsuite.ultimatecore.reforge.gui.ReforgeGUI;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class User {
  public String player;
  public String name;

  private transient ReforgeGUI runeTableGUI;
  
  public User(OfflinePlayer p) {
    this.player = p.getUniqueId().toString();
    this.name = p.getName();
    (HyperReforge.getInstance().getRunesManager()).users.put(this.player, this);
  }

  public static User getUser(OfflinePlayer p) {
      if (p == null)
       return null;
      if ((HyperReforge.getInstance().getRunesManager()).users == null)
      (HyperReforge.getInstance().getRunesManager()).users = new HashMap<>();
      return (HyperReforge.getInstance().getRunesManager()).users.containsKey(p.getUniqueId().toString()) ? (HyperReforge.getInstance().getRunesManager()).users.get(p.getUniqueId().toString()) : new User(p);
  }
  

  public ReforgeGUI getReforgeGUI() {
    if (this.runeTableGUI == null)
      this.runeTableGUI = new ReforgeGUI(this, HyperReforge.getInstance());
    return this.runeTableGUI;
  }
}
