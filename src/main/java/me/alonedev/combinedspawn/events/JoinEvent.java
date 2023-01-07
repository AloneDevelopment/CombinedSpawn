package me.alonedev.combinedspawn.events;

import me.alonedev.combinedspawn.CombinedSpawn;
import me.alonedev.combinedspawn.utils.ItemUtils;
import me.alonedev.combinedspawn.utils.Util;
import me.alonedev.combinedspawn.utils.Functions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private CombinedSpawn main;
    private ItemUtils itemUtils;
    private Functions playerFunctions;

    public JoinEvent(CombinedSpawn main) {
        this.main = main;
        this.itemUtils = new ItemUtils(main);
        this.playerFunctions = new Functions(main);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            event.setJoinMessage(Util.returnPlaceholders(main.getConfig().getString("First_Join_Message") ,player));
            Util.sendMultipleMsg(main.getConfig().getStringList("First_Join_MOTD") ,player);
            itemUtils.generateItems(player, "First_Join_Items");
            playerFunctions.teleportPlayer(player, "First_Join_Spawn_Location");
        }
        event.setJoinMessage(Util.returnPlaceholders(main.getConfig().getString("Join_Message"), player));
        Util.sendMultipleMsg(main.getConfig().getStringList("Join_MOTD"), player);
        itemUtils.generateItems(player,"Join_Items");
        playerFunctions.teleportPlayer(player, "Spawn_Location");
    }
}