package me.alonedev.combinedspawn.Commands;

import me.alonedev.combinedspawn.CombinedSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (sender instanceof Player) {
            if (CombinedSpawn.SpawnCommand == true) {
                if (sender instanceof Player) {
                    Location spawn = new Location(Bukkit.getWorld(CombinedSpawn.spawnworld), CombinedSpawn.x, CombinedSpawn.y, CombinedSpawn.z, CombinedSpawn.yaw, CombinedSpawn.pitch);
                    player.teleport(spawn);
                }
            }
        }
        return true;
    }
}