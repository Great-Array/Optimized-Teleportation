package me.great_array.optimized_teleportation;

import me.great_array.optimized_teleportation.Commands.TeleportAll;
import me.great_array.optimized_teleportation.Listeners.PlayerJoin;
import me.great_array.optimized_teleportation.Listeners.PlayerQuit;
import me.great_array.optimized_teleportation.Update.UpdateCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Teleport extends JavaPlugin {

    public static Teleport plugin;
    public static List<Player> playerList = new ArrayList<Player>();
    public static boolean teleportInProgress = false;
    public static boolean debug;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        debug = getConfig().getBoolean("Debug");
        setupConfig();
        registerListeners();
        setupCommands();
        updateCheck();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log("-------------------------------------------------------------------------------------\n" +
                "(Optimized-Teleportation) The plugin has been disabled, Thank you for using our plugin! :)\n" +
                "-------------------------------------------------------------------------------------\n");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
    }

    private void setupCommands() {
        this.getCommand("tpe").setExecutor(new TeleportAll());
    }

    private void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void log(String message) {
        System.out.println(message);
    }

    private static void updateCheck() {
        try {
            if (UpdateCheck.checkForUpdate()) {
                System.out.println("-------------------------------------------------------------------------------------");
                System.out.println("OPTIMIZED TELEPORT HAS GOTTEN A NEW UPDATE");
                System.out.println("Pleas consider updating to the newest version!");
                System.out.println("(Optimized-Teleportation) The plugin has been enabled, Developed by Great_Array with Love <3\n" +
                        "-------------------------------------------------------------------------------------");
            } else {
                System.out.println("-------------------------------------------------------------------------------------");
                System.out.println("You are on the latest version of our plugin!");
                System.out.println("(Optimized-Teleportation) The plugin has been enabled, Developed by Great_Array with Love <3\n" +
                        "-------------------------------------------------------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
