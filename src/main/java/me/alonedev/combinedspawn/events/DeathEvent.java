package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.constructors.DeathConstructor;
import me.alonedev.combinedspawn.utils.Functions;
import me.alonedev.combinedspawn.utils.Util;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

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
            death = DeathConstructor.getDeathType(nEvent.getDamager().getName().toUpperCase());
        } else {
            death = DeathConstructor.getDeathType(player.getLastDamageCause().getCause().name().toUpperCase());
        }

        if (death == null) {
            death = DeathConstructor.getDeathType("DEFAULT");
        }


        //Messages
        if (death.getPrivateMessage() != null) {
            Util.sendMsg(Util.returnPlaceholders(death.getPrivateMessage(), player), player);
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            Util.sendMsg(Util.returnPlaceholders(main.getConfig().getString("Deaths.Types.DEFAULT.Private_Message"), player), player);
        }
        if (death.getMessage() != null) {
            event.setDeathMessage(Util.returnPlaceholders(death.getMessage(), player));
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            Util.sendMsg(Util.returnPlaceholders(main.getConfig().getString("Deaths.Types.DEFAULT.Message"), player), player);
        }
        if (death.getTitle() != null) {
            player.sendTitle(Util.returnPlaceholders(death.getTitle(), player), Util.returnPlaceholders(death.getSubtitle(), player), death.getFadeIn(), death.getStay(), death.getFadeout());
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
        } else if (main.getConfig().getBoolean("Auto_Fill_Incomplete_Death_Types_With_Default")) {
            event.setKeepInventory(main.getConfig().getBoolean("Deaths.Types.DEFAULT.Keep_Inventory"));
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



    }
}