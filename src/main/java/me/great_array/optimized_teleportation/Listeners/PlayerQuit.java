package me.great_array.optimized_teleportation.Listeners;

import me.great_array.optimized_teleportation.Teleport;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerQuit implements Listener {

    List<Player> playerList = Teleport.playerList;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (playerList.contains(player))
            playerList.remove(player);

    }

}
