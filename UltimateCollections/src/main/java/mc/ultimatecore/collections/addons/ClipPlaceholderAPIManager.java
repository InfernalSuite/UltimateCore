package mc.ultimatecore.collections.addons;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.objects.PlayerCollections;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class ClipPlaceholderAPIManager extends PlaceholderExpansion {
    
    private final HyperCollections plugin;
    
    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin The instance of our plugin.
     */
    public ClipPlaceholderAPIManager(HyperCollections plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist() {
        return true;
    }
    
    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }
    
    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }
    
    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public @NotNull String getIdentifier() {
        return "collections";
    }
    
    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     * <p>
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
    
    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use Offlineplayer.getUniqueId()s in your requests.
     *
     * @param identifier A String containing the identifier/value.
     * @return possibly-null String of the requested identifier.
     */
    
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null || identifier == null) {
            return "";
        }
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId());
        switch (identifier) {
            case "unlocked":
                return playerCollection.getAllUnlocked() + "";
            case "total":
                return HyperCollections.getInstance().getCollections().getCollectionQuantity() + "";
        }
        for (String key : HyperCollections.getInstance().getCollections().collections.keySet()) {
            if (identifier.equalsIgnoreCase("rank_" + key))
                return String.valueOf(playerCollection.getRankPosition(key));
            if (identifier.equalsIgnoreCase("xp_" + key))
                return String.valueOf(playerCollection.getXP(key));
            if (identifier.equalsIgnoreCase("level_" + key))
                return String.valueOf(playerCollection.getLevel(key));
        }
        return null;
    }
}