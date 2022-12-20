package me.alonedev.combinedspawn.utils;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.constructors.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Functions {

    private CombinedSpawn main;

    public Functions(CombinedSpawn main) {
        this.main = main;
    }

    public void teleportPlayer(Player player, String configPath) {
        String world = main.getConfig().getString(configPath + ".world");
        Double x = main.getConfig().getDouble(configPath + ".x");
        Double y = main.getConfig().getDouble(configPath + ".y");
        Double z = main.getConfig().getDouble(configPath + ".z");
        int pitch = main.getConfig().getInt(configPath + ".pitch");
        int yaw = main.getConfig().getInt(configPath + ".yaw");

        Location teleportLocation = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        player.teleport(teleportLocation);

    }


    public enum EnumType {
        STRING,
        INT,
        LIST,
        BOOLEAN
    }

    public boolean isEnabled(Enum type, String config_path) {
        if (type == EnumType.STRING) {
            if (main.getConfig().getString(config_path) == null) {
                return false;
            }
        } else if (type == EnumType.INT) {
            if (main.getConfig().getInt(config_path) == 0) {
                return false;
            }
        } else if (type == EnumType.LIST) {
            if ((main.getConfig().getStringList(config_path) == null)) {
                return false;
            }
        } else if (type == EnumType.BOOLEAN) {
            if ((main.getConfig().getStringList(config_path) == null)) {
                return false;
            }
        }
        return true;

    }


    private static HashMap<String, Title> titles = new HashMap<>();

    public static void addTitle(String id, Title title){

        titles.put(id,title);

    }

    public static Title getTitle(String id) {
        return titles.get(id);
    }
}