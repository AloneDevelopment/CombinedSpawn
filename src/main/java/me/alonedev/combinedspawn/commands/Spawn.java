package me.alonedev.combinedspawn.commands;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.constructors.Title;
import me.alonedev.combinedspawn.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Spawn implements CommandExecutor {

    private final CombinedSpawn main;

    public Spawn(CombinedSpawn main) {
        this.main = main;
    }

    HashMap<Player, Integer> cooldowns = new HashMap<Player, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (main.getConfig().getBoolean("Options.Enable_Spawn_Teleport")) {
                cooldowns.put(player, main.getConfig().getInt("Options.Spawn_Cooldown"));

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        String time = Integer.toString(cooldowns.get(player));
                        if (cooldowns.get(player) == 0) {
                            Location spawn = new Location(Bukkit.getWorld(main.getConfig().getString("Spawn_Location.world")),
                                    main.getConfig().getDouble("Spawn_Location.x"),
                                    main.getConfig().getDouble("Spawn_Location.y"),
                                    main.getConfig().getDouble("Spawn_Location.z"),
                                    main.getConfig().getInt("Spawn_Location.yaw"),
                                    main.getConfig().getInt("Spawn_Location.pitch"));
                            player.teleport(spawn);
                            this.cancel();
                        } else {
                            if (main.getConfig().getBoolean("Options.Enable_Spawn_Countdown_Titles")) {
                                Title title = Functions.getTitle("Spawn_Teleporting");

                                String spawnTitle = PlaceholderAPI.setPlaceholders(player, title.getTitle());
                                String Subtitle = PlaceholderAPI.setPlaceholders(player, title.getSubtitle());
                                player.sendTitle(ChatColor.translateAlternateColorCodes('&', spawnTitle).
                                        replace("%cooldown%", time), Subtitle.
                                        replace("%cooldown%", time), title.getFadeIn(), title.getStay(), title.getFadeOut());

                                cooldowns.put(player, cooldowns.get(player) - 1);
                            }
                        }

                    }

                }.runTaskTimer(main, 20, 20);


            }
        }

        return true;
    }
}
