package com.infernalsuite.ultimatecore.farm.utils;

public enum XItem {
    SUGAR_CANE_BLOCK("SUGAR_CANE"),
    BEETROOT_BLOCK("BEETROOTS");

    public final String material;

    XItem(String material){
        this.material = material;
    }

    public static String getMaterial(String item){
        try {
            XItem xItem = XItem.valueOf(item);
            return xItem.material;
        }catch (Exception e){
            return item;
        }
    }
}
