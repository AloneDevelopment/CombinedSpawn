package me.alonedev.combinedspawn.Commands;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.Utils.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {


    private CombinedSpawn main;

    private final CombinedSpawn instance;

    public AdminCommands(final CombinedSpawn instance) {
        this.instance = instance;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals(CombinedSpawn.BASE_COMMAND)) {
            if (sender instanceof Player) {
                final Player p = (Player)sender;
                if(p.hasPermission(CombinedSpawn.BASE_PERMISSION+".use")) {
                    if(args.length == 0 || args.length > 1) {
                        this.instance.help(p);
                    } else if(args.length == 1) {
                        if(args[0].equals("reload")) {
                            if (p.hasPermission(CombinedSpawn.BASE_PERMISSION+".reload")) {
                                this.instance.loadSettings();
                                p.sendMessage(ChatColor.YELLOW+CombinedSpawn.PLUGIN_NAME+ChatColor.GREEN+" has been reloaded!");
                            } else p.sendMessage(ChatColor.RED+"No permission!");
                        } else this.instance.help(p);
                    }
                } else p.sendMessage(ChatColor.RED+"No permission!");
            } else if(sender instanceof Player){
                if(args.length == 0 || args.length > 1) {
                    this.instance.help(null);
                } else if(args.length == 1) {
                    if(args[0].equals("reload")) {
                        this.instance.loadSettings();
                        Util.consoleMsg(ChatColor.YELLOW+CombinedSpawn.PLUGIN_NAME+ChatColor.GREEN+" has been reloaded!");
                    } else this.instance.help(null);
                }
            } else {
                final Player p = (Player)sender;
                if(args.length == 0 || args.length > 1) {
                    this.instance.help(null);
                }

            }
            return true;
        }
        return false;
    }
}

