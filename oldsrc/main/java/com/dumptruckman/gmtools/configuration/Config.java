package com.dumptruckman.gmtools.configuration;

import com.dumptruckman.gmtools.GMTools;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman, SwearWord
 */
public enum Config {
    DEBUG_MODE("settings.debug_mode.enable", false, "# Enables debug mode."),
    SPAWN_WORLD("settings.spawn.world_name", "fly", "# World name for spawn."),
    BED_SPAWN_ITEM("settings.bed_spawn.item_id", 368, "# Item ID for bed spawning."),
    EXPLODE_INTERVAL("settings.explode_interval", 10, "# Explosion interval in ticks.")
    ;

    private String path;
    private Object def;
    private String[] comments;

    Config(String path, Object def, String...comments) {
        this.path = path;
        this.def = def;
        this.comments = comments;
    }

    public final Boolean getBoolean() {
        return config.getBoolean(path, (Boolean)def);
    }

    public final Integer getInteger() {
        return config.getInt(path, (Integer)def);
    }

    public final String getString() {
        return config.getString(path, (String)def);
    }

    /**
     * Retrieves the path for a config option
     * @return The path for a config option
     */
    private String getPath() {
        return path;
    }

    /**
     * Retrieves the default value for a config path
     * @return The default value for a config path
     */
    private Object getDefault() {
        return def;
    }

    /**
     * Retrieves the comment for a config path
     * @return The comments for a config path
     */
    private String[] getComments() {
        if (comments != null) {
            return comments;
        }

        String[] comments = new String[1];
        comments[0] = "";
        return comments;
    }

    private static CommentedConfiguration config;

    /**
     * Loads the configuration data into memory and sets defaults
     * @throws IOException
     */
    public static void load() throws IOException {
        // Make the data folders
        GMTools.getInstance().getDataFolder().mkdirs();

        // Check if the config file exists.  If not, create it.
        File configFile = new File(GMTools.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        // Load the configuration file into memory
        config = new CommentedConfiguration(new File(GMTools.getInstance().getDataFolder(), "config.yml"));
        config.load();

        // Sets defaults config values
        setDefaults();

        // Saves the configuration from memory to file
        config.save();
    }

    /**
     * Loads default settings for any missing config values
     */
    private static void setDefaults() {
        for (Config path : Config.values()) {
            config.addComment(path.getPath(), path.getComments());
            if (config.getString(path.getPath()) == null) {
                config.setProperty(path.getPath(), path.getDefault());
            }
        }
    }
}
