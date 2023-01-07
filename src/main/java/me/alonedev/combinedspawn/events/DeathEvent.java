package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.utils.Util;
import me.alonedev.combinedspawn.constructors.DeathConstructor;
import me.alonedev.combinedspawn.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    private CombinedSpawn main;
    private Functions functions;

    public DeathEvent(CombinedSpawn main) {
        this.main = main;
        this.functions = new Functions(main);
    }

    private EntityType entityKiller;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        EntityDamageEvent ede = player.getLastDamageCause();
        EntityDamageEvent.DamageCause dc = ede.getCause();

        DeathConstructor death = DeathConstructor.getDeathType(dc.name());
        DeathConstructor deathEntity = DeathConstructor.getDeathType(String.valueOf(entityKiller));

        if (death == null) {
            event.setDeathMessage(Util.returnPlaceholders(main.getConfig().getString("Deaths.Death_Message"), player));
            OnRespawn.cooldowns.put(player, main.getConfig().getInt("Deaths.Respawn_Cooldown"));
            return;
        }

        //Messages
        Util.sendMsg(Util.returnPlaceholders(death.getPrivateMessage(), player), player);
        event.setDeathMessage(Util.returnPlaceholders(death.getMessage(), player));
        player.sendTitle(Util.returnPlaceholders(death.getTitle(), player) , Util.returnPlaceholders(death.getSubtitle(), player), death.getFadeIn(),death.getStay(),death.getFadeout());
        OnRespawn.cooldowns.put(player, death.getCoolown());
    }


    @EventHandler
    public void getKiller(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();
            if (player.getHealth() <= event.getDamage()) {
                entityKiller = event.getDamager().getType();
            }

        }


    }



}