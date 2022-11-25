package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.utils.Functions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VoidTP implements Listener {

    private CombinedSpawn main;
    private Functions functions;

    public VoidTP(CombinedSpawn main) {
        this.main = main;
        this.functions = new Functions(main);
    }

    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(main.getConfig().getBoolean("VoidTeleport.Enable")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    event.setCancelled(true);
                    Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                        @Override
                        public void run() {
                            functions.teleportPlayer(player, "VoidTeleport.TeleportLocation");
                            player.setHealth(20);
                            player.setFallDistance(0);
                        }

                    }, 10L);
                }
            }

        }
    }

}