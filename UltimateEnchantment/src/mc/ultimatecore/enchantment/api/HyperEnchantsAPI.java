package mc.ultimatecore.enchantment.api;

import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface HyperEnchantsAPI {

    /**
     * Method to get a Enchanted Item
     *
     * @param itemStack ItemStack
     * @param hyperEnchant HyperEnchant
     * @param level int
     * @param reforged boolean
     * @return return enchanted item
     */
    ItemStack enchantItem(ItemStack itemStack, HyperEnchant hyperEnchant, int level, boolean reforged);

    /**
     * Method to remove enchant to an Item
     *
     * @param itemStack ItemStack
     * @param hyperEnchant HyperEnchant
     * @return return enchanted item
     */
    ItemStack removeEnchant(ItemStack itemStack, HyperEnchant hyperEnchant);

    /**
     * Method to know if item get enchantment by reforge
     *
     * @param itemStack ItemStack
     * @param hyperEnchant HyperEnchant
     * @return return if item get enchantment by reforge
     */
    boolean hasReforgedEnchantment(ItemStack itemStack, HyperEnchant hyperEnchant);

    /**
     * Method to get HyperEnchant instance from its enchantmentID
     *
     * @param enchantmentID String
     * @return return hyperenchant instance
     */
    HyperEnchant getEnchantmentInstance(String enchantmentID);

    /**
     * Method to check compatibility between an item and enchantment
     *
     * @param itemStack ItemStack
     * @param hyperEnchant HyperEnchant
     * @return return if enchant is compatible
     */
    boolean itemCanBeEnchanted(ItemStack itemStack, HyperEnchant hyperEnchant);

    /**
     * Method to check if item is enchanted with a specific level
     *
     * @param itemStack ItemStack
     * @param hyperEnchant HyperEnchant
     * @param level int
     * @return return if item is enchanted
     */
    boolean itemIsEnchanted(ItemStack itemStack, HyperEnchant hyperEnchant, int level);

    /**
     * Method to get all Enchantments availables for an item
     *
     * @param itemStack ItemStack
     * @return return item available enchantments
     */
    List<HyperEnchant> getAvailableEnchantments(ItemStack itemStack);


    /**
     * Method to get all HyperEnchantments of an item
     *
     * @param itemStack ItemStack
     * @return return item enchantments
     */
    Map<HyperEnchant, Integer> getItemEnchantments(ItemStack itemStack);

    /**
     * Method to get all Enchantments instances of plugin
     *
     * @return return item enchantments
     */
    List<HyperEnchant> getAllEnchantments();

    /**
     * Method to get all Enchantments instances of plugin
     *
     * @return return item enchantments
     */
    ItemStack getEnchantedBook(HyperEnchant hyperEnchant, int level);

}
