package com.dumptruckman.minecraft.matchmaker.util;

import com.dumptruckman.minecraft.matchmaker.api.config.MMConfig;
import com.dumptruckman.minecraft.pluginbase.config.AbstractYamlConfig;
import com.dumptruckman.minecraft.pluginbase.config.ConfigEntry;
import com.dumptruckman.minecraft.pluginbase.config.EntryBuilder;
import com.dumptruckman.minecraft.pluginbase.plugin.BukkitPlugin;
import com.dumptruckman.minecraft.pluginbase.util.Null;

import java.io.File;
import java.io.IOException;

/**
 * Commented Yaml implementation of Config.
 */
public class YamlPluginConfig extends AbstractYamlConfig<MMConfig> implements MMConfig {

    private static final ConfigEntry<Null> SETTINGS = new EntryBuilder<Null>(Null.class, "settings")
            .comment("# === [ MatchMaker Settings ] ===").build();

    public YamlPluginConfig(BukkitPlugin plugin, boolean doComments, File configFile, Class<? extends MMConfig>... configClasses) throws IOException {
        super(plugin, doComments, configFile, configClasses);
    }

    protected String getHeader() {
        return "# === [ MatchMaker Config ] ===";
    }
}
