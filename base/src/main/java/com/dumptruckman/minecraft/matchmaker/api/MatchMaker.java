package com.dumptruckman.minecraft.matchmaker.api;

import com.dumptruckman.minecraft.pluginbase.config.BaseConfig;
import com.dumptruckman.minecraft.pluginbase.plugin.PluginBase;

public interface MatchMaker<C extends BaseConfig> extends PluginBase<C> {

    ArenaManager getArenaManager();

    MapManager getMapManager();
}
