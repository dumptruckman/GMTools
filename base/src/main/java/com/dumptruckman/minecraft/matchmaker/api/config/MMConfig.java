package com.dumptruckman.minecraft.matchmaker.api.config;

import com.dumptruckman.minecraft.pluginbase.config.BaseConfig;
import com.dumptruckman.minecraft.pluginbase.config.ConfigEntry;
import com.dumptruckman.minecraft.pluginbase.config.EntryBuilder;

public interface MMConfig extends BaseConfig {

    ConfigEntry<String> EXAMPLE = new EntryBuilder<String>(String.class, "path.to.entry")
            .def("default").comment("# comments").build();
}
