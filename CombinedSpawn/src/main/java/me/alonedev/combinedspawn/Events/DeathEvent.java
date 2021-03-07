package me.alonedev.combinedspawn.Events;

import me.alonedev.combinedspawn.CombinedSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    private CombinedSpawn main;

    public DeathEvent (CombinedSpawn main) {
        this.main = main;
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = event.getEntity();
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                player.spigot().respawn();
                Location spawn = new Location(Bukkit.getWorld(CombinedSpawn.spawnworld), CombinedSpawn.x, CombinedSpawn.y, CombinedSpawn.z, CombinedSpawn.yaw, CombinedSpawn.pitch);
                player.teleport(spawn);
            }, 1L);

        }
    }
}