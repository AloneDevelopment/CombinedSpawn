package me.alonedev.combinedspawn;

import me.alonedev.combinedspawn.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CombinedSpawn extends JavaPlugin {

    //Data.yml

    private File dataFile = new File(getDataFolder(), "data.yml");
    private FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);

    @Override
    public void onEnable() {
        // Plugin startup logic
        Util.consoleMsg("--------------------------------------\n  \nCombinedSpawn has successfully loaded!\n  \n--------------------------------------");

        this.saveDefaultConfig();

        if(!dataFile.exists()) {
            saveResource("data.yml", false);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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