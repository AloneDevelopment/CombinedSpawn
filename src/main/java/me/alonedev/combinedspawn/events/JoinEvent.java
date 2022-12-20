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
    private Functions functions;

    public JoinEvent(CombinedSpawn main) {
        this.main = main;
        this.itemUtils = new ItemUtils(main);
        this.functions = new Functions(main);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            event.setJoinMessage(Util.returnPlaceholders(main.getConfig().getString("JoinEvent.FirstJoin.Message") ,player));
            Util.sendMultipleMsg(main.getConfig().getStringList("JoinEvent.FirstJoin.MOTD") ,player);
            itemUtils.generateItems(player, "JoinEvent.FirstJoin");
            functions.teleportPlayer(player, "JoinEvent.FirstJoin.spawn");
        }
        event.setJoinMessage(Util.returnPlaceholders(main.getConfig().getString("JoinEvent.Join.Message"), player));
        Util.sendMultipleMsg(main.getConfig().getStringList("JoinEvent.Join.MOTD"), player);
        itemUtils.generateItems(player,"JoinEvent.Join");
        functions.teleportPlayer(player, "JoinEvent.Join.spawn");
    }
}