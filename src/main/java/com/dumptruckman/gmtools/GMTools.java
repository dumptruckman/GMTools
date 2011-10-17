package com.dumptruckman.gmtools;

import com.dumptruckman.gmtools.commands.CmdChunkyChunkSetShop;
import com.dumptruckman.gmtools.commands.CmdChunkyChunkSetSpleef;
import com.dumptruckman.gmtools.commands.FireballCommand;
import com.dumptruckman.gmtools.commands.TextCommand;
import com.dumptruckman.gmtools.configuration.Config;
import com.dumptruckman.gmtools.listeners.ChunkyPlayerEvents;
import com.dumptruckman.gmtools.listeners.GMToolsPlayerListener;
import com.dumptruckman.gmtools.permissions.Perms;
import com.dumptruckman.gmtools.util.Logging;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getchunky.chunky.Chunky;
import org.getchunky.chunky.event.ChunkyEvent;
import org.getchunky.chunky.exceptions.ChunkyUnregisteredException;
import org.getchunky.chunky.module.ChunkyCommand;

import java.io.IOException;
import java.util.HashSet;

public class GMTools extends JavaPlugin {

    private static GMTools INSTANCE;
    private static HashSet<Player> fireBallPlayers = new HashSet<Player>();
    private static boolean chunky = false;

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
        hookChunky();

        Logging.info("is enabled!", true);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Event.Priority.Lowest, this);
    }

    private void registerCommands() {
        getCommand("text").setExecutor(new TextCommand());
        getCommand("fireball").setExecutor(new FireballCommand());
    }

    private void hookChunky() {
        Logging.debug("Hooking chunky...");
        PluginManager pm = getServer().getPluginManager();
        Plugin chunky = pm.getPlugin("Chunky");
        if (chunky != null) {
            this.chunky = true;
            Logging.debug("Chunky hooked!");
            registerChunkyCommands();
            registerChunkyEvents();
        }
    }

    public void registerChunkyCommands() {
        try {
            ChunkyCommand ChunkyChunkSetShop = new ChunkyCommand("shop", new CmdChunkyChunkSetShop(),
                    Chunky.getModuleManager().getCommandByName("chunky.chunk.set"))
                    .setDescription("Allows people to barter in chunk.")
                    .setHelpLines("Usage: /chunky chunk set shop <true|false>", "Allow/disallow people to set up signs in this chunk")
                    .setInGameOnly(true)
                    .register();
            ChunkyCommand ChunkyChunkSetSpleef = new ChunkyCommand("spleef", new CmdChunkyChunkSetSpleef(),
                    Chunky.getModuleManager().getCommandByName("chunky.chunk.set"))
                    .setDescription("Allows people to destroy snow/dirt blocks.")
                    .setHelpLines("Usage: /chunky chunk set spleef <true|false>", "Allow/disallow people to place/break dirt/snow in this chunk.")
                    .setInGameOnly(true)
                    .register();
        } catch (ChunkyUnregisteredException e) {
            Logging.warning(e.getMessage());
        }
    }

    public void registerChunkyEvents() {
        ChunkyPlayerEvents p = new ChunkyPlayerEvents();
        Chunky.getModuleManager().registerEvent(ChunkyEvent.Type.PLAYER_BUILD, p, ChunkyEvent.Priority.Normal, this);
        Chunky.getModuleManager().registerEvent(ChunkyEvent.Type.PLAYER_DESTROY, p, ChunkyEvent.Priority.Normal, this);
    }

    public static boolean isChunky() {
        return chunky;
    }

    public static GMTools getInstance() {
        return INSTANCE;
    }

    public static HashSet<Player> getFireBallPlayers() {
        return fireBallPlayers;
    }
}
