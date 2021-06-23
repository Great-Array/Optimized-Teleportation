package me.great_array.optimized_teleportation.Commands;

import com.sun.tools.classfile.Annotation;
import me.great_array.optimized_teleportation.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class TeleportAll extends Thread implements CommandExecutor {

    List<Player> playerList = Teleport.playerList;

    String noPermission = Teleport.noPermission;
    String finishedMessage = Teleport.finishedTeleport;
    String teleportInProgress = Teleport.teleportInProgress;
    String returnInProgress = Teleport.returnInProgress;
    String nobodyOnline = Teleport.nobodyOnline;
    String teleportStarted = Teleport.teleportStarted;

    int seconds = Teleport.teleportCooldown;
    int playerAmount = Teleport.playerAmount;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("optimized.teleport.all") || player.hasPermission("optimized.teleport.admin")) {

                if (Bukkit.getOnlinePlayers().size() > 1) {

                    Location location = player.getLocation();

                    if (Teleport.activeTeleport) {
                        sendMessage(player, teleportInProgress);
                    } else if (Teleport.activeReturn) {
                        sendMessage(player, returnInProgress);
                    } else {

                        teleportStarted = teleportStarted.replace("$user", player.getName());
                        broadcast(teleportStarted);

                        Teleport.activeTeleport = true;

                        int i = 0;
                        for (Player player_1 : Bukkit.getOnlinePlayers()) {
                            if (!player_1.getName().equalsIgnoreCase(player.getName())) {
                                i++;
                                String name = player_1.getName();
                                playerList.add(player_1);

                                String x = String.valueOf(player_1.getLocation().getX());
                                String y = String.valueOf(player_1.getLocation().getY());
                                String z = String.valueOf(player_1.getLocation().getZ());
                                String world = player_1.getWorld().getName();
                                String total = player_1.getName() + "%Z%" + x + "%Z%" + y + "%Z%" + z + "%Z%" + world;
                                Teleport.returnTesting.add(total);

                            }
                        }

                        new BukkitRunnable() {
                            public void run() {

                                if (!playerList.isEmpty()) {

                                    for (int i = 0; i < playerAmount; i++) {
                                        if (!playerList.isEmpty()) {
                                            playerList.get(0).teleport(location);
                                            playerList.remove(0);
                                        } else {
                                            break;
                                        }
                                    }

                                } else {
                                    broadcast(finishedMessage);
                                    Teleport.activeTeleport = false;
                                    cancel();
                                    return;
                                }

                            }
                        }.runTaskTimer(Teleport.getInstance(), 0L, seconds * 20);
                        return true;
                    }

                } else {
                    sendMessage(player, nobodyOnline);
                }

            } else {
                sendMessage(player, noPermission);
            }

        } else {
            System.out.println("All players have been teleported to you.. \n" +
                    "Just kidding, we can't teleport players to a console haha");
        }

        return false;
    }

    private static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static void broadcast(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
