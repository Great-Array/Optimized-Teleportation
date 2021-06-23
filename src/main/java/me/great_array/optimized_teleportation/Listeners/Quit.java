package me.great_array.optimized_teleportation.Listeners;

import me.great_array.optimized_teleportation.Teleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;

public class Quit implements Listener {

    List<Player> playerList = Teleport.playerList;
    List<Player> toReturn = Teleport.toReturn;

    @EventHandler
    public void onQuite(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (playerList.contains(player))
            playerList.remove(player);

        if (toReturn.contains(player))
            toReturn.remove(player);

    }

}
