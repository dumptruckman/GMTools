package com.dumptruckman.minecraft.matchmaker.command;

import com.dumptruckman.minecraft.matchmaker.MatchMakerPlugin;
import com.dumptruckman.minecraft.pluginbase.plugin.command.PluginCommand;

public abstract class MMCommand extends PluginCommand<MatchMakerPlugin> {

    public MMCommand(MatchMakerPlugin plugin) {
        super(plugin);
    }
}
