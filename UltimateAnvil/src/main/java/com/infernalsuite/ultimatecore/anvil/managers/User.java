package com.infernalsuite.ultimatecore.anvil.managers;

import com.infernalsuite.ultimatecore.anvil.HyperAnvil;
import com.infernalsuite.ultimatecore.anvil.gui.HyperAnvilGUI;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class User {
  public String player;
  
  public String name;

  private transient HyperAnvilGUI anvilGUI;
  
  public User(OfflinePlayer p) {
    this.player = p.getUniqueId().toString();
    this.name = p.getName();
    (HyperAnvil.getInstance().getRunesManager()).users.put(this.player, this);
  }
  
  public static User getUser(String p) {
    if ((HyperAnvil.getInstance().getRunesManager()).users == null)
      (HyperAnvil.getInstance().getRunesManager()).users = new HashMap<>();
    return (HyperAnvil.getInstance().getRunesManager()).users.get(p);
  }
  
  public static User getUser(OfflinePlayer p) {
    if (p == null)
      return null; 
    if ((HyperAnvil.getInstance().getRunesManager()).users == null)
      (HyperAnvil.getInstance().getRunesManager()).users = new HashMap<>();
    return (HyperAnvil.getInstance().getRunesManager()).users.containsKey(p.getUniqueId().toString()) ? (HyperAnvil.getInstance().getRunesManager()).users.get(p.getUniqueId().toString()) : new User(p);
  }
  
  public static boolean userExist(OfflinePlayer p) {
    if (p == null)
      return false; 
    if ((HyperAnvil.getInstance().getRunesManager()).users == null)
      return false; 
    return (HyperAnvil.getInstance().getRunesManager()).users.containsKey(p.getUniqueId().toString());
  }
  

  public HyperAnvilGUI getAnvilGUI() {
    if (this.anvilGUI == null)
      this.anvilGUI = new HyperAnvilGUI(this);
    return this.anvilGUI;
  }
}
