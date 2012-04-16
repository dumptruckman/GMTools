package com.dumptruckman.minecraft.gmtools.util;

import com.dumptruckman.minecraft.gmtools.api.GMConfig;
import com.dumptruckman.minecraft.pluginbase.config.AbstractYamlConfig;
import com.dumptruckman.minecraft.pluginbase.plugin.BukkitPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Commented Yaml implementation of Config.
 */
public class YamlPluginConfig extends AbstractYamlConfig<GMConfig> implements GMConfig {

    public YamlPluginConfig(BukkitPlugin plugin, boolean doComments, File configFile, Class<? extends GMConfig>... configClasses) throws IOException {
        super(plugin, doComments, configFile, configClasses);
    }

    protected String getHeader() {
        return "# === [ GMTools Config ] ===";
    }
}
