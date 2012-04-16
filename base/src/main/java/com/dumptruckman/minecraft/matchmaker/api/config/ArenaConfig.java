package com.dumptruckman.minecraft.matchmaker.api.config;

import com.dumptruckman.minecraft.pluginbase.config.ConfigEntry;
import com.dumptruckman.minecraft.pluginbase.config.EntryBuilder;

/**
 * Contains the configuration for a single arena.
 */
public interface ArenaConfig extends RegionConfig<ArenaConfig> {
    
    ConfigEntry<String> NAME = new EntryBuilder<String>(String.class, "name").def("").build();

    ConfigEntry<String> WORLD = new EntryBuilder<String>(String.class, "world").def("").build();
}
