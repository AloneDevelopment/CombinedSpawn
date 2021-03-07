package me.alonedev.combinedspawn.Events;

import me.alonedev.combinedspawn.CombinedSpawn;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    private CombinedSpawn main;

    public LeaveEvent (CombinedSpawn main) {
        this.main = main;
    }


    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        if (CombinedSpawn.LeaveMessages == true) {
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', CombinedSpawn.Quitmessage)
                    .replace("%line%", "\n").
                            replace("%player%", event.getPlayer().getDisplayName()));
        }
    }
}
