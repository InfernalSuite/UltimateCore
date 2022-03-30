package mc.ultimatecore.enchantment.managers;

import lombok.Getter;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.addons.ClipPlaceholderAPIManager;
import mc.ultimatecore.enchantment.addons.MVDWPlaceholderAPIManager;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Bukkit;

@Getter
public class AddonsManager{
    private final EnchantmentsPlugin plugin;
    private ClipPlaceholderAPIManager placeholderAPI;
    private MVDWPlaceholderAPIManager mvdwPlaceholderAPI;

    public AddonsManager(EnchantmentsPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        String name = plugin.getDescription().getName();
        if(isPlugin("PlaceholderAPI")){
            placeholderAPI = new ClipPlaceholderAPIManager(plugin);
            placeholderAPI.register();
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e["+name+"] &aSuccessfully hooked into PlaceholderAPI!"));
        }
        if(isPlugin("MVDWPlaceholderAPI")){
            mvdwPlaceholderAPI = new MVDWPlaceholderAPIManager(plugin);
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e["+name+"] &aSuccessfully hooked into MVDWPlaceholderAPI!"));
        }
    }

    private boolean isPlugin(String name){
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }



}