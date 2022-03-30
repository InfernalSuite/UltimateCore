package com.infernalsuite.ultimatecore.farm.configs;

import com.cryptomorin.xseries.XMaterial;

import java.util.*;

public class Inventories {


    public Item mainMenuItem = new Item(XMaterial.PLAYER_HEAD, 1, "&6&l&n%region_type% Regions", Arrays.asList("", "&eClick to view %region_type% setting!"));

    //TIME
    public Item changeTime = new Item(XMaterial.CLOCK, 1, "&e&lTime", Arrays.asList("", "&7Min Time: &6%min_time%","", "&7Max Time: &6%max_time%", "", "&6» &7This is the time that takes block", "  &7to appear again."));
    public Item blockBroken = new Item(XMaterial.DIRT, 1, "&e&lBlock Broken", Arrays.asList("&7Type: &6%block_type%","", "&6» &7This is the block that you break", "  &7initially."));
    public Item blockWhile = new Item(XMaterial.DIRT, 1, "&e&lBlock While Regen", Arrays.asList("&7Type: &6%block_type%", "", "&6» &7This is the block that'll appear after", "  &7block broken."));
    public Item blocksAfter = new Item(XMaterial.DIRT, 1, "&e&lBlocks After", Arrays.asList("", "&6» &7These are the blocks that", "  &7have chance to  appear after", "  &7while regen block.", "", "&6» &eClick to view all blocks!"));
    public Item regionsItem = new Item(XMaterial.DIRT, 1, "&e&lRegions", Arrays.asList("", "&6» &7These are the blocks that", "  &7have chance to  appear after", "  &7while regen block.", "", "&6» &eClick to view all blocks!"));


    //Confirm
    public Item confirmDelete = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 50, 1, "&aYes", new ArrayList<>());
    public Item cancelDelete = new Item(XMaterial.RED_STAINED_GLASS_PANE, 50, 1, "&cNo", new ArrayList<>());


    public Item nextPage = new Item(XMaterial.ARROW, 50, 1, "&eNext Page", new ArrayList<>());

    public Item previousPage = new Item(XMaterial.ARROW, 48, 1, "&ePrevious Page", new ArrayList<>());

    public Item background = new Item(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());

    public Item regionBackground = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());

    public Item closeButton = new Item(XMaterial.BARRIER, 49, 1, "&cClose Menu", new ArrayList<>());

    public Item isTripleBlock = new Item(XMaterial.ENDER_EYE, 1, "&e&lIs Triple Block", Arrays.asList("", "&7Enabled: &6%status%", "",  "&6» &eIf it's enabled it'll regen block", "  &eabove and below of broken block", "  &eif they were of the same type."));

    public Item nextPhase = new Item(XMaterial.PLAYER_HEAD, 1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19" ,1, " ", Collections.emptyList());


    public Set<Integer> regionSlots = new HashSet<>(Arrays.asList(0, 1 ,9, 7, 8, 17, 36, 45, 46, 44, 52, 53));

    public static class Item {

        public XMaterial material;
        public int amount;
        public String title;
        public String headData;
        public String headOwner;
        public List<String> lore;
        public Integer slot;

        public Item(Item item) {
            this.material = item.material;
            this.amount = item.amount;
            this.lore = item.lore;
            this.title = item.title;
        }

        public Item(XMaterial material, int amount, String title, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
        }

        public Item(XMaterial material, int slot, int amount, String title, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
            this.slot = slot;
        }

        public Item(XMaterial material, int slot, String headData, int amount, String title, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
            this.slot = slot;
            this.headData = headData;
        }

        public Item(XMaterial material, int slot, int amount, String title, String headOwner, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
            this.headOwner = headOwner;
            this.slot = slot;
        }

        public Item(XMaterial material, int amount, String title, String headOwner, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
            this.headOwner = headOwner;
        }
    }
}
