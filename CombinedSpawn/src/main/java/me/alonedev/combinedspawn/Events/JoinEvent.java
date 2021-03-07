package me.alonedev.combinedspawn.Events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class JoinEvent implements Listener {


    private CombinedSpawn main;

    public JoinEvent (CombinedSpawn main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {

        String username = event.getPlayer().getDisplayName();
        int PlayersJoined = main.getDataConfig().getInt("PlayersJoined");

        Player player = event.getPlayer();

        if(!player.hasPlayedBefore()) {
            //Replacements
            PlayersJoined++;
            String joinReplacement = "#" + PlayersJoined;

            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', CombinedSpawn.FirstJoinmessage)
                    .replace("%player%", username).
                     replace("%joinplacement%", joinReplacement).
                     replace("%line%", "\n"));

            if (CombinedSpawn.ForceTeleportOnFirstJoin == true) {
                Location spawn = new Location(Bukkit.getWorld(CombinedSpawn.spawnworld), CombinedSpawn.x, CombinedSpawn.y, CombinedSpawn.z, CombinedSpawn.yaw, CombinedSpawn.pitch);
                player.teleport(spawn);
            }

            try {
                main.getDataConfig().save(main.getDataFile());
            } catch(IOException error) {
                error.printStackTrace();
                Util.consoleMsg("Error saving data.yml, Make sure to report this to AloneMusk immediately.");
            }
        } else {
            String joinReplacement = "#" + PlayersJoined;
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', CombinedSpawn.Joinmessage)
                            .replace("%player%", username).
                            replace("%joinplacement%", joinReplacement).
                            replace("%line%", "\n"));

            if (CombinedSpawn.ForceTeleportOnJoin == true) {
                Location spawn = new Location(Bukkit.getWorld(CombinedSpawn.spawnworld), CombinedSpawn.x, CombinedSpawn.y, CombinedSpawn.z, CombinedSpawn.yaw, CombinedSpawn.pitch);
                player.teleport(spawn);
            }

        }

    }

}
