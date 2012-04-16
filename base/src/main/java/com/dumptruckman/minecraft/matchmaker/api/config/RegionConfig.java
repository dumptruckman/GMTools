package com.dumptruckman.minecraft.matchmaker.api.config;

import com.dumptruckman.minecraft.pluginbase.config.ConfigEntry;
import com.dumptruckman.minecraft.pluginbase.config.EntryBuilder;

public interface RegionConfig<V extends RegionConfig> extends Config<V> {

    ConfigEntry<Integer> MIN_X = new EntryBuilder<Integer>(Integer.class, "min.x").build();
    ConfigEntry<Integer> MIN_Y = new EntryBuilder<Integer>(Integer.class, "min.y").build();
    ConfigEntry<Integer> MIN_Z = new EntryBuilder<Integer>(Integer.class, "min.z").build();

    ConfigEntry<Integer> MAX_X = new EntryBuilder<Integer>(Integer.class, "max.x").build();
    ConfigEntry<Integer> MAX_Y = new EntryBuilder<Integer>(Integer.class, "max.y").build();
    ConfigEntry<Integer> MAX_Z = new EntryBuilder<Integer>(Integer.class, "max.z").build();
}
