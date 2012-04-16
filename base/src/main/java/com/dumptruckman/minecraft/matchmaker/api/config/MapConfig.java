package com.dumptruckman.minecraft.matchmaker.api.config;

import com.dumptruckman.minecraft.pluginbase.config.ConfigEntry;
import com.dumptruckman.minecraft.pluginbase.config.EntryBuilder;

/**
 * Contains the configuration for a single Map.
 */
public interface MapConfig extends Config {

    ConfigEntry<String> NAME = new EntryBuilder<String>(String.class, "name").def("").build();
}
