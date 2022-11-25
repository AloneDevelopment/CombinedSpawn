package me.alonedev.combinedspawn.constructors;

import java.util.HashMap;

public class ItemConstructor {

    private String name;
    private int level;
    private int durability;
    private int slot;
    private String[] lore;
    private String[] enchants;

    public ItemConstructor (String name, int level, int durability, int slot, String[] lore, String[] enchants) {
        this.name = name;
        this.level = level;
        this.durability = durability;
        this.slot = slot;
        this.lore = lore;
        this.enchants = enchants;
    }

    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public int getDurability() {
        return durability;
    }
    public int getSlot() {
        return slot;
    }
    public String[] getLore() {
        return lore;
    }
    public String[] getEnchants() {
        return enchants;
    }

    private static HashMap<String, ItemConstructor> items = new HashMap<String, ItemConstructor>();

    public static void addItem(String id, ItemConstructor item) {
        items.put(id,item);
    }
    public static ItemConstructor getItem(String id) {
        return items.get(id);
    }
}