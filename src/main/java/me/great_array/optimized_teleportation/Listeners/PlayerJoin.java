package me.great_array.optimized_teleportation.Listeners;

import me.great_array.optimized_teleportation.Teleport;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoin implements Listener {

    List<Player> playerList = Teleport.playerList;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Teleport.teleportInProgress) {
            if (!playerList.contains(player)) {
                playerList.add(player);
            }
        }

    }

}
