package me.alonedev.combinedspawn.Events;

import me.alonedev.combinedspawn.CombinedSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VoidTP implements Listener {

    private CombinedSpawn main;

    public VoidTP (CombinedSpawn main) {
        this.main = main;
    }




    @EventHandler
    public void VoidTP(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (CombinedSpawn.VoidTPEnable == true) {
                if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    event.setCancelled(true);
                    }
                    Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                        @Override
                        public void run() {
                            Location spawn = new Location(Bukkit.getWorld(CombinedSpawn.spawnworld), CombinedSpawn.x, CombinedSpawn.y, CombinedSpawn.z, CombinedSpawn.yaw, CombinedSpawn.pitch);
                            player.teleport(spawn);
                            }

                    }, 10L);

                }
            }
        }

    }


