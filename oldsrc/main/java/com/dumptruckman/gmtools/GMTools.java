package com.dumptruckman.gmtools;

import com.dumptruckman.gmtools.commands.*;
import com.dumptruckman.gmtools.configuration.Config;
import com.dumptruckman.gmtools.listeners.GMToolsPlayerListener;
import com.dumptruckman.gmtools.permissions.Perms;
import com.dumptruckman.gmtools.util.Logging;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;

public class GMTools extends JavaPlugin {

    private static GMTools INSTANCE;
    private static HashSet<Player> fireBallPlayers = new HashSet<Player>();
    private static HashSet<Player> explosionPlayers = new HashSet<Player>();
    private static HashSet<Player> explodingPlayers = new HashSet<Player>();

    private final GMToolsPlayerListener playerListener = new GMToolsPlayerListener();

    public void onDisable() {
        Logging.info("is disabled!", true);
    }

    public void onEnable() {
        INSTANCE = this;

        Logging.load(this);
        Perms.load(this);

        PluginManager pm = getServer().getPluginManager();
        // Loads the configuration
        try {
            Config.load();
        } catch (IOException e) {  // Catch errors loading the config file and exit out if found.
            Logging.severe("Encountered an error while loading the configuration file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        registerEvents();
        registerCommands();
        registerSchedule();

        Logging.info("is enabled!", true);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Event.Priority.Lowest, this);
    }

    private void registerCommands() {
        getCommand("text").setExecutor(new TextCommand());
        getCommand("fireball").setExecutor(new FireballCommand());
        getCommand("explode").setExecutor(new ExplosionCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
    }

    public void registerSchedule() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                getExplodingPlayers().addAll(getExplosionPlayers());
            }
        }, 0, Config.EXPLODE_INTERVAL.getInteger());
    }

    public static GMTools getInstance() {
        return INSTANCE;
    }

    public static HashSet<Player> getFireBallPlayers() {
        return fireBallPlayers;
    }

    public static HashSet<Player> getExplosionPlayers() {
        return explosionPlayers;
    }

    public static HashSet<Player> getExplodingPlayers() {
        return explodingPlayers;
    }
}
