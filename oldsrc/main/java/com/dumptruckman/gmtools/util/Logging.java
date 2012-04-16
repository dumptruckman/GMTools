package com.dumptruckman.gmtools.util;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * @author dumptruckman
 */
public class Logging {

    private static Logger LOG;
    private static String NAME = "";
    private static String VERSION = "";

    public static void load(JavaPlugin plugin) {
        PluginDescriptionFile pdf = plugin.getDescription();
        NAME = pdf.getName();
        VERSION = pdf.getVersion();
        LOG = Logger.getLogger("Minecraft." + NAME);
    }

    private static String getString(String message, boolean showVersion) {
        String string = "[" + NAME;
        if (showVersion) string += " " + VERSION;
        return string += "] " + message;
    }

    public static Logger getLog() {
        return LOG;
    }

    public static String getNameVersion() {
        return NAME + VERSION;
    }

    public static void info(String message) {
        info(message, false);
    }

    public static void info(String message, boolean showVersion) {
        LOG.info(getString(message, showVersion));
    }

    public static void debug(String message) {
        //if (Config.isDebugging())
            LOG.info(getString(message, true));
    }

    public static void warning(String message) {
        warning(message, false);
    }

    public static void warning(String message, boolean showVersion) {
        LOG.warning(getString(message, showVersion));
    }

    public static void severe(String message) {
        severe(message, false);
    }

    public static void severe(String message, boolean showVersion) {
        LOG.severe(getString(message, showVersion));
    }

}