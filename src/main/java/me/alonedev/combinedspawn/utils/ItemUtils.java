package me.alonedev.combinedspawn.utils;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    private CombinedSpawn main;

    public ItemUtils (CombinedSpawn main) {
        this.main = main;
    }

    public ItemStack createItem(final Material material, final String name, final ArrayList<String> lore, String event) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(lore);

        //Enchants

        meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchant)), enchantLvl, true);
        item.setItemMeta(meta);
        return item;
    }

    public void generateItems(Player player, String event) {
        List<String> Items = main.getConfig().getStringList(event + ".Items");
        List<String> itemEnchants = main.getConfig().getStringList(event + ".ItemEnchants");

        for (String enchant : itemEnchants) {
            String[] enchants = enchant.split(";");
            String enchantment = enchants[0];
            int enchantLvl = Integer.parseInt(enchants[1]);
            String itemApplicable = enchants[2];
        }

        for (String item : Items) {
            String[] items = item.split(";");
            String name = items[0];
            Material material = Material.valueOf(items[1]);
            int slot = Integer.parseInt(items[2]);
            String[] lores = items[3].split(",");
            ArrayList<String> formatLore = new ArrayList<String>();
            for (String lore : lores) {
                String PAPIlore = PlaceholderAPI.setPlaceholders(player, lore);
                formatLore.add(PAPIlore);
            }

            String formatName = PlaceholderAPI.setPlaceholders(player, name);

        }
    }
}