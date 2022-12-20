package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

        String QuitMessage = main.getConfig().getString("Quit_Message");

        Player player = event.getPlayer();
        QuitMessage = PlaceholderAPI.setPlaceholders(player, QuitMessage);
        if (main.getConfig().getBoolean("Enable_Leave_Messages") == true) {
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', QuitMessage)
                    .replace("%line%", "\n").
                    replace("%player%", event.getPlayer().getDisplayName()));
        }
    }
}