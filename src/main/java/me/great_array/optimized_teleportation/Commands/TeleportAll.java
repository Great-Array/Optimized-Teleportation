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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TeleportAll extends Thread implements CommandExecutor {
    List<Player> playerList = Teleport.playerList;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String noPermission = Teleport.plugin.getConfig().getString("Messages.NoPermission");
        String broadcast = Teleport.plugin.getConfig().getString("Messages.Broadcast");
        String finished = Teleport.plugin.getConfig().getString("Messages.FinishedTeleporting");
        String alreadyInProgress = Teleport.plugin.getConfig().getString("TeleportAlreadyInProgress");
        long seconds = Teleport.plugin.getConfig().getInt("TelepotCoolDownSeconds");
        long playerAmount = Teleport.plugin.getConfig().getInt("AmountOfPlayersToTeleportEachTime");
        boolean debug = Teleport.debug;

        broadcast.replace("$user", sender.getName());

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("optimized_teleport.all")) {
                Location loc = player.getLocation();

                if (Teleport.teleportInProgress) {
                    // TODO: SOMEONE HAS ALREADY STARTED A TELEPORT ALL, WAIT FOR IT TO FINISH
                    assert alreadyInProgress != null;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', alreadyInProgress));
                } else {
                    // TODO: TELEPORT ALL IS AVAILABLE, TELEPOT PLAYERS
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', broadcast ));

                    Teleport.teleportInProgress = true;

                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        if (!player1.getName().equalsIgnoreCase(player.getName())) {
                            playerList.add(player1);
                            if (debug) { System.out.println("Added " + player1.getName() + " to Teleport List"); }
                        }
                    }

                    new BukkitRunnable() {
                        public void run() {

                            if (!playerList.isEmpty()) {
                                // TODO: PLAYER LIST IS NOT EMPTY, CONTINUE TO TELEPORT

                                for (int i = 0; i < playerAmount; i++) {
                                    if (!playerList.isEmpty()) {
                                        if (debug) { System.out.println("Teleported Player to " + loc); }
                                        playerList.get(0).teleport(loc);
                                        playerList.remove(0);
                                    } else {
                                        break;
                                    }
                                }

                            } else {
                                // TODO: PLAYER LIST IS EMPTY, STOP TIMER
                                assert finished != null;
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', finished ));
                                Teleport.teleportInProgress = false;
                                cancel();
                                return;
                            }

                        }
                    }.runTaskTimer(Teleport.plugin, 0L, seconds * 20);
                    return true;
                }

            } else {
                assert noPermission != null;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
            }

        } else {
            sender.sendMessage("*Teleports Players to you*");
            sender.sendMessage("Just kidding, the Console can't use this command!");
        }

        return false;
    }
}
