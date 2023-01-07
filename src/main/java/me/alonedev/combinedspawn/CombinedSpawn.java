package me.alonedev.combinedspawn;

import me.alonedev.combinedspawn.events.DeathEvent;
import me.alonedev.combinedspawn.events.JoinEvent;
import me.alonedev.combinedspawn.events.VoidTP;
import me.alonedev.combinedspawn.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CombinedSpawn extends JavaPlugin {

    //Data.yml

    private final File dataFile = new File(getDataFolder(), "data.yml");
    private final FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);

    @Override
    public void onEnable() {
        // Plugin startup logic
        Util.consoleMsg("--------------------------------------\n  \nCombinedSpawn has successfully loaded!\n  \n--------------------------------------");

        this.saveDefaultConfig();
        if(!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        registerCommands();
        registerEvents();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new VoidTP(this), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
    }

    public void registerCommands() {

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


}