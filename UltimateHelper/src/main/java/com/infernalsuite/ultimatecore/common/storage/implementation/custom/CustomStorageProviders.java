package com.infernalsuite.ultimatecore.common.storage.implementation.custom;

public class CustomStorageProviders {

    private CustomStorageProviders() { throw new UnsupportedOperationException(); }

    private static CustomStorageProvider provider = null;

    public static void register(CustomStorageProvider provider) { CustomStorageProviders.provider = provider; }

    public static CustomStorageProvider getProvider() {
        if (provider == null) throw new IllegalStateException("Provider not present");
        return provider;
    }

}
