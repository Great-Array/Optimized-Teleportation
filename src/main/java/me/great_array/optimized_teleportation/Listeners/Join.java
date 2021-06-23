package me.great_array.optimized_teleportation.Listeners;

import me.great_array.optimized_teleportation.Teleport;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class Join implements Listener {

    List<Player> playerList = Teleport.playerList;
    List<String> updateMessage = Teleport.updateMessage;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Teleport.update) {
            if (player.hasPermission("optimized.teleport.admin")) {
                String update = String.valueOf(updateMessage).replace("[", "").replace("]", "").replace(",", "");
                sendMessage(player, update);
            }
        }

        if (Teleport.activeTeleport) {
            if (!playerList.contains(player)) {
                playerList.add(player);
            }
        }
    }

    private static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
