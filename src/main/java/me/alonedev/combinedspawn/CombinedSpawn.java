package me.alonedev.combinedspawn;

import me.alonedev.combinedspawn.commands.Spawn;
import me.alonedev.combinedspawn.constructors.DeathConstructor;
import me.alonedev.combinedspawn.constructors.Title;
import me.alonedev.combinedspawn.events.*;
import me.alonedev.combinedspawn.utils.Functions;
import me.alonedev.combinedspawn.utils.Metrics;
import me.alonedev.combinedspawn.utils.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class CombinedSpawn extends JavaPlugin {

    public static Economy econ = null;
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        // Plugin startup logic
        Util.consoleMsg("--------------------------------------\n  \nCombinedSpawn has successfully loaded!\n  \n--------------------------------------");
        Logger logger = this.getLogger();
        this.saveDefaultConfig();
        registerCommands();
        registerEvents();
        loadData();

        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
        getServer().getPluginManager().registerEvents(new VoidTP(this), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnRespawn(this), this);
        if (this.getConfig().getBoolean("Deaths.No_Move_On_Respawn")) {
            getServer().getPluginManager().registerEvents(new NoMove(this), this);
        }
        this.getCommand("spawn").setExecutor(new Spawn(this));

        if (getConfig().getBoolean("Enable_Bstats")) {
            int pluginId = 10651;
            Metrics metrics = new Metrics(this, pluginId);
        }


        if (setupEconomy()) {
            log.severe(String.format("[%s] - Vault Is required for economy!", getDescription().getName()));
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.info("Could not find PlaceholderAPI! This plugin is required for placeholders.");
        }



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

    public void loadData() {

        FileConfiguration cfg = this.getConfig();

        cfg.getConfigurationSection("Deaths.Types").getKeys(false).forEach(x -> {
            String DeathMessage = cfg.getString("Deaths.Types." + x + ".Message", cfg.getString("Player_Death.Death_Message"));
            String PrivateDeathMessage = cfg.getString("Deaths.Types." + x + ".Private_Message", "&cYou died! Unfortunately you lost %moneylost%");
            int MoneyPenalty = cfg.getInt("Deaths.Types." + x + ".Money_Penalty", 100);
            int levelPenalty = cfg.getInt("Deaths.Types." + x + ".Level_Penalty", 1);
            int expPenalty = cfg.getInt("Deaths.Types." + x + ".exp_Penalty", 1);
            String title = cfg.getString("Deaths.Types." + x + ".Title.Death_Title", "You Died!");
            String subtitle = cfg.getString("Deaths.Types." + x + ".Title.Death_Subtitle", "Hello");
            int fadeIn = cfg.getInt("Deaths.Types." + x + ".Title.FadeIn", 10);
            int stay = cfg.getInt("Deaths.Types." + x + ".Title.Stay", 100);
            int fadeOut = cfg.getInt("Deaths.Types." + x + ".Title.FadeOut", 10);
            int cooldown = cfg.getInt("Deaths.Types." + x + ".Respawn_Cooldown", 5);
            boolean keepinventory = cfg.getBoolean("Deaths.Types." + x + ".Keep_Inventory");
            boolean keeplevels = cfg.getBoolean("Deaths.Types." + x + ".Keep_Levels");
            DeathConstructor newDeathType = new DeathConstructor(DeathMessage, PrivateDeathMessage, title, subtitle, MoneyPenalty, levelPenalty, expPenalty, fadeIn, stay, fadeOut, cooldown, keepinventory, keeplevels);
            DeathConstructor.addDeathType(x, newDeathType);
        });

        //Titles
        cfg.getConfigurationSection("Titles").getKeys(false).forEach(x -> {
            String title = cfg.getString("Titles." + x + ".Title");
            String subtitle = cfg.getString("Titles." + x + ".Subtitle");
            int fadeIn = cfg.getInt("Titles." + x + ".FadeIn");
            int stay = cfg.getInt("Titles." + x + ".Stay");
            int fadeOut = cfg.getInt("Titles." + x + ".FadeOut");
            int delay = cfg.getInt("Titles." + x + ".delay");
            Title newTitle = new Title(title, subtitle, fadeIn, stay, fadeOut);
            Title.setDelay(delay);
            Title.addTitle(x, newTitle);
        });
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }



}