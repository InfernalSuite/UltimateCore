package mc.ultimatecore.collections.configs;

import lombok.Getter;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.objects.Category;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.objects.DebugType;
import mc.ultimatecore.collections.utils.Utils;
import mc.ultimatecore.helper.files.YAMLFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;


@Getter
public class Collections extends YAMLFile {
    
    private int collectionsQuantity;
    
    private Map<Category, List<String>> collectionsCategory;
    
    private List<Category> categories;
    
    public Map<String, Collection> collections;
    
    public Collections(HyperCollections plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadCollections();
    }
    
    @Override
    public void reload() {
        super.reload();
        this.loadCollections();
    }
    
    private void loadCollections() {
        categories = new ArrayList<>();
        collections = new HashMap<>();
        collectionsCategory = new HashMap<>();
        collectionsQuantity = 0;
        ConfigurationSection categoriesSection = getConfig().getConfigurationSection("categories");
        if (categoriesSection != null) {
            int i = 0;
            for (String key : categoriesSection.getKeys(false)) {
                String name = getConfig().getString("categories." + key + ".name");
                String displayName = getConfig().getString("categories." + key + ".displayName");
                Category category = new Category(name, displayName);
                categories.add(category);
                i++;
            }
            if (i > 0)
                HyperCollections.getInstance().sendDebug("&e[HyperCollections] &aSuccessfully loaded " + i + " categories!", DebugType.COLORED);
        }
        YamlConfiguration cf = getConfig();
        
        ConfigurationSection configurationSection = cf.getConfigurationSection("collections");
        if (configurationSection == null) return;
        int i = 0;
        try {
            if (collections == null) collections = new HashMap<>();
            for (String key : configurationSection.getKeys(false)) {
                String finalKey = getConfig().getString("collections." + key + ".key");
                boolean enabled = cf.contains("collections." + key + ".enabled") ? cf.getBoolean("collections." + key + ".enabled") : true;
                if (!enabled) continue;
                String displayName = getConfig().getString("collections." + key + ".name");
                int slot = getConfig().getInt("collections." + key + ".slot");
                if (getConfig().contains("collections." + key + ".categorie")) {
                    HyperCollections.getInstance().sendDebug("&e[HyperCollections] &cError collection " + key + " contains 'categorie' replace it with 'category'!", DebugType.COLORED);
                    continue;
                }
                Optional<Category> optionalCategory = getCategory(getConfig().getString("collections." + key + ".category"));
                if (!optionalCategory.isPresent()) continue;
                Category category = optionalCategory.get();
                HashMap<Integer, List<String>> rewards = Utils.getCollectionObject(getConfig(), key, "rewards");
                HashMap<Integer, List<String>> commands = Utils.getCollectionObject(getConfig(), key, "commands");
                HashMap<Integer, Double> requirements = Utils.getCollectionRequirements(getConfig(), key);
                collections.put(finalKey, new Collection(displayName, category, finalKey, slot, rewards, requirements, commands));
                i++;
            }
        } catch (Exception e) {
            HyperCollections.getInstance().sendDebug("Error Loading Collections!, Disabling Plugin", DebugType.LOG);
            e.printStackTrace();
        }
        if (i > 0)
            HyperCollections.getInstance().sendDebug("&e[HyperCollections] &aSuccessfully loaded " + i + " collections!", DebugType.COLORED);
    }
    
    public boolean collectionExist(String key) {
        return collections.containsKey(key);
    }
    
    public Collection getCollection(String key) {
        return collections.getOrDefault(key, null);
    }
    
    public List<String> getCollectionQuantity(Category category) {
        if (!collectionsCategory.containsKey(category))
            collectionsCategory = new HashMap<Category, List<String>>() {{
                put(category, new ArrayList<String>() {{
                            for (Collection collection : collections.values())
                                if (collection.getCategory() == category) {
                                    add(collection.getKey());
                                }
                        }}
                );
            }};
        return collectionsCategory.get(category);
    }
    
    public int getCollectionQuantity() {
        if (collectionsQuantity == 0)
            collectionsQuantity = collections.size();
        return collectionsQuantity;
    }
    
    public Optional<Category> getCategory(String name) {
        return categories.stream().filter(category -> category.getName().equalsIgnoreCase(name)).findFirst();
    }
}
