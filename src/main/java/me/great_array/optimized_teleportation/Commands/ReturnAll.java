package me.great_array.optimized_teleportation.Commands;

import me.great_array.optimized_teleportation.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReturnAll implements CommandExecutor {


    String noPermission = Teleport.noPermission;
    String finishedMessage = Teleport.finishedReturn;
    String returnInProgress = Teleport.returnInProgress;
    String teleportInProgress = Teleport.teleportInProgress;
    String nobodyOnline = Teleport.nobodyOnline;
    String noReturn = Teleport.noReturn;
    String returnStarted = Teleport.returnStarted;

    int seconds = Teleport.teleportCooldown;
    int playerAmount = Teleport.playerAmount;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("optimized.teleport.all") || player.hasPermission("optimized.teleport.admin")) {

                if (Bukkit.getOnlinePlayers().size() > 1) {

                    if (Teleport.activeTeleport) {
                        sendMessage(player, teleportInProgress);
                    } else if (Teleport.activeReturn) {
                        sendMessage(player, returnInProgress);
                    } else {

                        if (Teleport.returnTesting.size() > 0) {

                            returnStarted = returnStarted.replace("$user", player.getName());
                            broadcast(returnStarted);
                            Teleport.activeReturn = true;

                            new BukkitRunnable() {
                                public void run() {

                                    if (!Teleport.returnTesting.isEmpty()) {

                                        for (int i = 0; i < playerAmount; i++) {

                                            if (Teleport.returnTesting.isEmpty()) {
                                                break;
                                            }

                                            String test = Teleport.returnTesting.get(0);
                                            Teleport.returnTesting.remove(0);

                                            String[] test2 = test.split("%Z%");

                                            if (getOnlinePlayersNames().contains(test2[0])) {
                                                Player player1 = Bukkit.getPlayer(test2[0]);


                                                double x = Double.parseDouble(test2[1]);
                                                double y = Double.parseDouble(test2[2]);
                                                double z = Double.parseDouble(test2[3]);
                                                String world = test2[4];

                                                Location location = new Location(Bukkit.getWorld(world), x, y, z, 0, 0);

                                                player1.teleport(location);
                                            }

                                        }

                                    } else {
                                        broadcast(finishedMessage);
                                        Teleport.activeReturn = false;
                                        cancel();
                                        return;
                                    }

                                }
                            }.runTaskTimer(Teleport.getInstance(), 0L, seconds * 20);
                            return true;


                        } else {
                            sendMessage(player, noReturn);
                        }

                    }

                } else {
                    sendMessage(player, nobodyOnline);
                }

            } else {
                sendMessage(player, noPermission);
            }

        } else {
            System.out.println("Sorry but the console can not execute this command" +
                    "It would make sense if it could tho, right?");
        }

        return false;
    }

    private static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static void broadcast(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static List<String> getOnlinePlayersNames() {
        List<String> playerList = new ArrayList<String>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerList.add(player.getName());
        }
        return playerList;
    }
}
