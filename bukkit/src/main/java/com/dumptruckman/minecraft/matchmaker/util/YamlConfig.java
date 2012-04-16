package com.dumptruckman.minecraft.matchmaker.util;

import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.dumptruckman.minecraft.pluginbase.config.AbstractYamlConfig;
import com.dumptruckman.minecraft.pluginbase.plugin.BukkitPlugin;

import java.io.File;
import java.io.IOException;

public class YamlConfig<V extends Config<V>> extends AbstractYamlConfig<V> implements Config<V> {

    public YamlConfig(BukkitPlugin plugin, boolean doComments, File configFile, Class<? extends V>... configClasses) throws IOException {
        super(plugin, doComments, configFile, configClasses);
    }

    protected String getHeader() {
        return "";
    }
}
