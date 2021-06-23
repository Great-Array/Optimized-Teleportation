package me.great_array.optimized_teleportation;

import me.great_array.optimized_teleportation.Commands.ReturnAll;
import me.great_array.optimized_teleportation.Commands.TeleportAll;
import me.great_array.optimized_teleportation.Listeners.Join;
import me.great_array.optimized_teleportation.Listeners.Quit;
import me.great_array.optimized_teleportation.Util.Update.CheckUpdate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Teleport extends JavaPlugin {

    private static Teleport instance;
    public static List<Player> playerList = new ArrayList<Player>();
    public static List<Player> toReturn = new ArrayList<Player>();
    public static List<String> returnTesting = new ArrayList<String>();

    public static ArrayList<String> updateMessage = new ArrayList<String>();

    public static boolean activeTeleport = false;
    public static boolean activeReturn = false;
    public static boolean update;

    public static int version = 1;

    public static int teleportCooldown;
    public static int playerAmount;
    public static int configVersion;

    public static String noPermission;
    public static String teleportInProgress;
    public static String returnInProgress;
    public static String finishedTeleport;
    public static String finishedReturn;
    public static String teleportStarted;
    public static String returnStarted;
    public static String nobodyOnline;
    public static String noReturn;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        setupConfig();
        setupMessages();
        registerListeners();
        setupCommands();
        updateCheck();

        updateMessage.add("&f &r \n");
        updateMessage.add("                     &8(&e&lOptimized Teleport&r&8)\n\n");
        updateMessage.add(" ");
        updateMessage.add(" &fHey, theres a new version of &eOptimized Teleport &favailable!\n");
        updateMessage.add(" &fConsider updating to get the latest &efeatures &fand &ebug fixes!\n\n");
        updateMessage.add(" &fUpdate Link: &ehttps://www.spigotmc.org/resources/92866/");
        updateMessage.add(" ");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(
                "Optimized Teleportation has been disabled, thank you for using my plugin! :)\n");
    }

    private void setupConfig() {
        saveDefaultConfig();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new Join(), this);
        Bukkit.getPluginManager().registerEvents(new Quit(), this);
    }

    private void setupCommands() {
        this.getCommand("tpe").setExecutor(new TeleportAll());
        this.getCommand("tper").setExecutor(new ReturnAll());
    }

    private static void updateCheck() {
        try {
            if (CheckUpdate.checkForUpdate()) {
                System.out.println(" ");
                log(" ");
                log("&6OPTIMIZED TELEPORT HAS GOTTEN A NEW UPDATE!");
                log("&ePlease consider updating to the newest version!");
                log("&eUpdate Link: &ehttps://www.spigotmc.org/resources/92866/");
                log("&8(&6TPE&8) &eThe plugin has been enabled, developed by Great_Array with love <3");
                update = true;
            } else {
                log(" ");
                log("&eYou are on the latest version of &eTPE");
                log("&8(&6TPE&8) &eThe plugin has been enabled, developed by Great_Array with love <3");
                update = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupMessages() {
        teleportCooldown = getConfig().getInt("Settings.TeleportCooldown");
        playerAmount = getConfig().getInt("Settings.PlayerAmount");
        configVersion = getConfig().getInt("Config");
        noPermission = getConfig().getString("Messages.NoPermission");
        teleportInProgress = getConfig().getString("Messages.TeleportInProgress");
        returnInProgress = getConfig().getString("Messages.ReturnInProgress");
        finishedTeleport = getConfig().getString("Messages.FinishedTeleport");
        finishedReturn = getConfig().getString("Messages.FinishedReturn");
        teleportStarted = getConfig().getString("Messages.TeleportStarted");
        returnStarted = getConfig().getString("Messages.ReturnStarted");
        nobodyOnline = getConfig().getString("Messages.NobodyOnline");
        noReturn = getConfig().getString("Messages.NoReturn");
    }

    public static Teleport getInstance() {
        return instance;
    }

    private static void log(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
