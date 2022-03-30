package mc.ultimatecore.collections.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mc.ultimatecore.collections.objects.PlayerCollections;
import mc.ultimatecore.collections.serializer.adapters.EnumTypeAdapter;
import mc.ultimatecore.collections.serializer.adapters.PlayerCollectionsAdapter;


public class GSON {
    
    private final Gson adapter;
    
    public GSON() {
        this.adapter = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping()
                                          .registerTypeAdapterFactory(EnumTypeAdapter.ENUM_FACTORY)
                                          .enableComplexMapKeySerialization()
                                          .registerTypeAdapter(PlayerCollections.class, new PlayerCollectionsAdapter())
                                          .create();
    }
    
    public String toString(PlayerCollections pd) {
        return this.adapter.toJson(pd, PlayerCollections.class);
    }
    
    public PlayerCollections fromString(String data) {
        return adapter.fromJson(data, PlayerCollections.class);
    }
    
}