package me.alonedev.combinedspawn.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Util {

    private static boolean initialised = false;
    private static Method getHandle;
    private static Method openBook;

    //Sends a message to a player
    public static void sendMsg(String msg, Player p) {
        if (p == null) consoleMsg(msg);
        else p.sendMessage(msg);
    }

    public static void sendMultipleMsg(List<String> messages, Player p) {
        for (String message : messages) {
            String msg = PlaceholderAPI.setPlaceholders(p, message);
            String a = ChatColor.translateAlternateColorCodes('&', msg);
            Util.sendMsg(a, p);
        }
    }



    public static String returnPlaceholders(String message, Player p) {
        return ChatColor.translateAlternateColorCodes('&',PlaceholderAPI.setPlaceholders(p,message));
    }

    //Sends a message to a player if they have permission
    public static void sendIfPermitted(String perm, String msg, Player player) {
        if (player.hasPermission(perm)) player.sendMessage(msg);
    }

    //Sends a message to the console
    public static void consoleMsg(String msg) {
        if (msg != null) Bukkit.getServer().getConsoleSender().sendMessage(msg);
    }

    public static String replaceColors(String in) {
        if (in == null) return null;
        return in.replace("&", "\u00A7");
    }

    //Sends an error message to the console
    public static void errorMsg(Exception e) {
        String error = e.toString();
        for (StackTraceElement x : e.getStackTrace()) {
            error += "\n\t" + x.toString();
        }
        consoleMsg(ChatColor.RED + "ERROR: " + error);
    }

    //Converts an int array to a string
    public static String intArrayToString(int[] arr) {
        String res = "";
        for (int a : arr) {
            res += a + "";
        }
        return res;
    }

    public static String[] readFile(Reader reader) {
        String out = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null)
                out += line + "\n";
            br.close();
            return out.split("\n");
        } catch (Exception e) {
            errorMsg(e);
        }
        return null;
    }

    public static String[] readFile(File file) {
        try {
            return readFile(new FileReader(file));
        } catch (Exception e) {
            errorMsg(e);
            return null;
        }
    }

    public static void writeFile(File file, String out) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(out);
            bw.close();
        } catch (Exception e) {
            errorMsg(e);
        }
    }

    public static final String stacksToBase64(final ItemStack[] contents) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(contents.length);
            for (ItemStack stack : contents) dataOutput.writeObject(stack);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray()).replace("\n", "").replace("\r", "");
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static final ItemStack[] stacksFromBase64(final String data) {
        if (data == null || Base64Coder.decodeLines(data).equals(null))
            return new ItemStack[]{};

        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        BukkitObjectInputStream dataInput = null;
        ItemStack[] stacks = null;

        try {
            dataInput = new BukkitObjectInputStream(inputStream);
            stacks = new ItemStack[dataInput.readInt()];
        } catch (IOException e) {
            Util.errorMsg(e);
        }

        for (int i = 0; i < stacks.length; i++) {
            try {
                stacks[i] = (ItemStack) dataInput.readObject();
            } catch (IOException | ClassNotFoundException e) {
                try {
                    dataInput.close();
                } catch (IOException ignored) {
                }
                Util.errorMsg(e);
                return null;
            }
        }

        try {
            dataInput.close();
        } catch (IOException ignored) {
        }

        return stacks;
    }

    public static ItemStack createGuiItem(Inventory GUI, final Material material, final String name, final int slot, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        GUI.setItem(slot, item);
        return item;
    }




}