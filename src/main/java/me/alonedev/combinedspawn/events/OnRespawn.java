package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.constructors.Title;
import me.alonedev.combinedspawn.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class OnRespawn implements Listener {

    private CombinedSpawn main;

    public OnRespawn(CombinedSpawn main) {
        this.main = main;
    }

    public static HashMap<Player, Integer> cooldowns = new HashMap<Player, Integer>();
    public static HashMap<Player, Boolean> Isdead = new HashMap<Player, Boolean>();

    @EventHandler
    public void PlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        Isdead.put(player, true);
        if (main.getConfig().getBoolean("Deaths.Enable_Death_Teleport")) {
            Location spawn = new Location(Bukkit.getWorld(main.getConfig().getString("Spawn_Location.world")),
                    main.getConfig().getDouble("Spawn_Location.x"),
                    main.getConfig().getDouble("Spawn_Location.y"),
                    main.getConfig().getDouble("Spawn_Location.z"),
                    main.getConfig().getInt("Spawn_Location.yaw"),
                    main.getConfig().getInt("Spawn_Location.pitch"));
            event.setRespawnLocation(spawn);

        }
        if (cooldowns.get(player) != 0) {
            player.setGameMode(GameMode.valueOf(main.getConfig().getString("Deaths.Gamemode_Before_Respawn")));


            new BukkitRunnable() {

                @Override
                public void run() {
                    String time = Integer.toString(cooldowns.get(player));
                    if (cooldowns.get(player) == 0) {
                        player.setGameMode(GameMode.valueOf(main.getConfig().getString("Deaths.Gamemode_After_Respawn")));
                        Isdead.put(player, false);
                        this.cancel();

                    } else {
                        if (main.getConfig().getBoolean("Deaths.Enable_Respawn_Countdown_Titles")) {
                            Title title = Functions.getTitle("RESPAWN");

                            String spawnTitle = PlaceholderAPI.setPlaceholders(player, title.getTitle());
                            String Subtitle = PlaceholderAPI.setPlaceholders(player, title.getSubtitle());
                            player.sendTitle(ChatColor.translateAlternateColorCodes('&', spawnTitle).
                                    replace("%cooldown%", time), ChatColor.translateAlternateColorCodes('&', Subtitle).
                                    replace("%cooldown%", time), title.getFadeIn(), title.getStay(), title.getFadeOut());
                            ;

                            cooldowns.put(player, cooldowns.get(player) - 1); }
                    }

                }

            }.runTaskTimer(main, 20, 20);
        }

    }
}
