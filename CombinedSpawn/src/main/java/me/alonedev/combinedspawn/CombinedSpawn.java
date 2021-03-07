package me.alonedev.combinedspawn;

import me.alonedev.combinedspawn.Commands.AdminCommands;
import me.alonedev.combinedspawn.Commands.Spawn;
import me.alonedev.combinedspawn.Events.DeathEvent;
import me.alonedev.combinedspawn.Events.JoinEvent;
import me.alonedev.combinedspawn.Events.LeaveEvent;
import me.alonedev.combinedspawn.Events.VoidTP;
import me.alonedev.combinedspawn.Mechanics.CommandsTab;
import me.alonedev.combinedspawn.Utils.ConfigUpdater;
import me.alonedev.combinedspawn.Utils.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CombinedSpawn extends JavaPlugin {


    //Config Options
    public static int joinPlacement;


    private String mainPath;
    public static final String PLUGIN_NAME = "CombinedSpawn";
    public static final String PREFIX = ChatColor.GOLD+"["+PLUGIN_NAME+"] ";
    public static String Joinmessage;
    public static String FirstJoinmessage;
    public static int x;
    public static int y;
    public static int z;
    public static int yaw;
    public static int pitch;
    public static String spawnworld;
    public static String Quitmessage;
    public static final String BASE_COMMAND = "CS";
    public static final String BASE_PERMISSION = "CS.use";
    public static boolean VoidTPEnable;
    public static boolean ForceTeleportOnJoin;
    public static boolean SpawnMessages;
    public static boolean SpawnCommand;
    public static boolean LeaveMessages;
    public static boolean FirstJoinMessage;
    public static boolean ForceTeleportOnFirstJoin;



    //Data.yml

    private File dataFile = new File(getDataFolder(), "data.yml");
    private FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);

    @Override
    public void onEnable() {
        // Plugin startup logic
        Util.consoleMsg("--------------------------------------\n  \nCombinedSpawn has successfully loaded!\n  \n--------------------------------------");


        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        this.loadSettings();

        if(!dataFile.exists()) {
            saveResource("data.yml", false);
        }

        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
        getServer().getPluginManager().registerEvents(new VoidTP(this), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);

        this.getCommand("spawn").setExecutor(new Spawn());
        this.getCommand(BASE_COMMAND).setExecutor(new AdminCommands(this));
        this.getCommand(BASE_COMMAND).setTabCompleter(new CommandsTab());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Util.consoleMsg("--------------------------------------\n  \nCombinedSpawn has successfully unloaded!\n  \n--------------------------------------");
    }




    public void loadSettings() {
        this.mainPath = this.getDataFolder().getPath()+"/";

        //This gets the active config.yml (not the default)
        final File configfile = new File(this.mainPath, "config.yml");

        //This updates the active config.yml with new options and comments from the default config.yml
        final ConfigUpdater updater = new ConfigUpdater(this.getTextResource("config.yml"), configfile);
        final FileConfiguration cfg; //Config object
        cfg = updater.updateConfig(configfile, PREFIX);

        //Load settings from config.yml here:
        //General format is: cfg.get<Java type>(<key>, <default value>);
        this.Joinmessage = cfg.getString("Join_Message", "Hello %player%, Welcome to server");
        this.FirstJoinmessage = cfg.getString("First_Join_Message", "Hello %player%, Welcome to server (%joinPlacement%)");
        this.Quitmessage = cfg.getString("Quit_Message", "%player% left the server");

        //Options
        this.VoidTPEnable = cfg.getBoolean("Enable_Void_TP", true);
        this.ForceTeleportOnJoin = cfg.getBoolean("Enable_Force_Teleport", true);
        this.SpawnCommand = cfg.getBoolean("Enable_Spawn_Command", true);
        this.SpawnMessages = cfg.getBoolean("Enable_Spawn_Messages", true);
        this.LeaveMessages = cfg.getBoolean("Enable_Leave_Messages", true);
        this.FirstJoinMessage = cfg.getBoolean("Enable_First_Join_Messages", true);
        this.ForceTeleportOnFirstJoin = cfg.getBoolean("Enable_First_Join_Force_Teleport", true);


        //Spawn Coords
        this.x = cfg.getInt("Spawn_Location.x");
        this.y = cfg.getInt("Spawn_Location.y");
        this.z = cfg.getInt("Spawn_Location.z");
        this.yaw = cfg.getInt("Spawn_Location.yaw");
        this.pitch = cfg.getInt("Spawn_Location.pitch");
        this.spawnworld = cfg.getString("Spawn_Location.world");
}

//
// YAML
//

    public void saveYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public File getDataFile() {
        return dataFile;
    }




    public void help(final Player sender) {
        if(sender != null) {
            //If the sender is a player, sends the help message to the player
            if(!sender.hasPermission(BASE_PERMISSION+".use") && !sender.hasPermission(BASE_PERMISSION+".*")) return;
            sender.sendMessage(ChatColor.GREEN+PLUGIN_NAME+" commands:");
            sender.sendMessage(ChatColor.AQUA+"/"+BASE_COMMAND+ChatColor.WHITE+" - shows this help message");
            Util.sendIfPermitted(BASE_PERMISSION+".reload", ChatColor.AQUA+"/"+BASE_COMMAND+" reload"+ChatColor.WHITE+" - reloads config.yml", sender);
        } else {
            //If the sender is the console, sends the help message to the console
            Util.consoleMsg(ChatColor.GREEN+PLUGIN_NAME+" commands:");
            Util.consoleMsg(ChatColor.AQUA+"/"+BASE_COMMAND+ChatColor.WHITE+" - shows this help message");
            Util.consoleMsg(ChatColor.AQUA+"/"+BASE_COMMAND+" reload"+ChatColor.WHITE+" - reloads config.yml");
        }
    }

}