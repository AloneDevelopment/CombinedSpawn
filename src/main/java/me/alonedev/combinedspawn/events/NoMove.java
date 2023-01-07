package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoMove implements Listener {


    private final CombinedSpawn main;

    public NoMove(CombinedSpawn main) {
        this.main = main;
    }

    @EventHandler
    public void RespawnCooldown(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (main.getConfig().getBoolean("Deaths.No_Move_On_Respawn")) {
            if (OnRespawn.Isdead.get(player) != null) {
                if (OnRespawn.Isdead.get(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }


}
