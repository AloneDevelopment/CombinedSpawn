package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.constructors.DeathConstructor;
import me.alonedev.combinedspawn.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class DeathEvent implements Listener {

    private final CombinedSpawn main;

    public DeathEvent(CombinedSpawn main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        DeathConstructor death;

        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
            if (nEvent.getDamager() instanceof Player) {
                death = DeathConstructor.getDeathType("PLAYER");
                processDeath(player, death, event, nEvent);
                return;
            }
            death = DeathConstructor.getDeathType(nEvent.getDamager().getName().toUpperCase());
            Util.sendMsg(nEvent.getDamager().getName().toUpperCase(), player);
            processDeath(player, death, event, nEvent);
        } else {
            death = DeathConstructor.getDeathType(player.getLastDamageCause().getCause().name().toUpperCase());
            Util.sendMsg(player.getLastDamageCause().getCause().name().toUpperCase(), player);
            processDeath(player, death, event, null);
        }







    }

    public void processDeath(Player player, DeathConstructor death, PlayerDeathEvent event, EntityDamageByEntityEvent nEvent) {
        if (death == null) {
            death = DeathConstructor.getDeathType("DEFAULT");
        }

        String killer = "";

        if (nEvent != null) {
            if (nEvent.getDamager().getName() != null) {
                killer = nEvent.getDamager().getName();
            }
        }

        Random random = new Random();

        //Messages
        if (death.getPrivateMessage() != null) {
            Util.sendMsg(Util.returnPlaceholders(ChatColor.translateAlternateColorCodes('&', death.getPrivateMessage()).replace("%killer%", killer), player), player);
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            Util.sendMsg(Util.returnPlaceholders(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Deaths.Types.DEFAULT.Private_Message")).replace("%killer%", killer), player), player);
        }
        if (death.getMessages() != null) {
            event.setDeathMessage(Util.returnPlaceholders(ChatColor.translateAlternateColorCodes('&', death.getMessages().get(random.nextInt(death.getMessages().size()))).replace("%killer%", killer), player));
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            Util.sendMsg(Util.returnPlaceholders(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Deaths.Types.DEFAULT.Message")).replace("%killer%", killer), player), player);
        }
        if (death.getTitle() != null) {
            player.sendTitle(Util.returnPlaceholders(ChatColor.translateAlternateColorCodes('&', death.getTitle()).replace("%killer%", killer), player), Util.returnPlaceholders(ChatColor.translateAlternateColorCodes('&', death.getSubtitle()).replace("%killer%", killer), player), death.getFadeIn(), death.getStay(), death.getFadeout());
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            player.sendTitle(Util.returnPlaceholders(main.getConfig().getString("Deaths.Types.DEFAULT.Titles.Title"), player), Util.returnPlaceholders(main.getConfig().getString("Deaths.Types.DEFAULT.Titles.Subtitle"), player), main.getConfig().getInt("Deaths.Types.DEFAULT.Titles.FadeIn"), main.getConfig().getInt("Deaths.Types.DEFAULT.Titles.Stay"), main.getConfig().getInt("Deaths.Types.DEFAULT.Titles.FadeOut"));
        }
        if (death.getCoolown() != 0) {
            OnRespawn.cooldowns.put(player, death.getCoolown());
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            OnRespawn.cooldowns.put(player, main.getConfig().getInt("Deaths.Types.DEFAULT.Respawn_Cooldown"));
        }

        if (death.getInventory() != null) {
            event.setKeepInventory(death.getInventory());
            Util.sendMsg("Keep Inventory: " + death.getInventory(), player);
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            event.setKeepInventory(main.getConfig().getBoolean("Deaths.Types.DEFAULT.Keep_Inventory"));
            Util.sendMsg("Keep Inventory: " + main.getConfig().getBoolean("Deaths.Types.DEFAULT.Keep_Inventory"), player);
        }
        Util.sendMsg(String.valueOf(event.getKeepInventory()), player);
        if (!event.getKeepInventory()) {
            Util.sendMsg(event.getDrops().toString(), player);
        }

        if (death.getKeepLevel() != null) {
            event.setKeepLevel(death.getKeepLevel());
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            event.setKeepLevel(main.getConfig().getBoolean("Deaths.Types.DEFAULT.Keep_Level"));
        }

        CombinedSpawn.econ.withdrawPlayer(player, death.getMoneyPenalty());
        if (player.getLevel() - death.getLevelPenalty() > 0) {
            player.setLevel(player.getLevel() - death.getLevelPenalty());
        }
        if (player.getExp() - death.getLevelPenalty() > 0) {
            player.setExp(player.getExp() - death.getLevelPenalty());
        }

        if (main.getConfig().getBoolean("Deaths.Skip_Death_Screen")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> player.spigot().respawn(), 5);
        }

        if (nEvent != null) {
            if (nEvent.getDamager().getName() != null) {
                Player player1 = Bukkit.getPlayer(killer);
                if (player1 != null) {
                    CombinedSpawn.econ.depositPlayer(player1, main.getConfig().getInt("Deaths.Types.PLAYER.Killer_Money_Reward"));
                    player1.giveExp(main.getConfig().getInt("Deaths.Types.PLAYER.Killer_Exp_Reward"));
                }
            }
        }
    }
}