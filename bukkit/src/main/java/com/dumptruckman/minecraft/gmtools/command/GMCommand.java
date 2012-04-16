package com.dumptruckman.minecraft.gmtools.command;

import com.dumptruckman.minecraft.gmtools.GMToolsPlugin;
import com.dumptruckman.minecraft.pluginbase.plugin.command.PluginCommand;

public abstract class GMCommand extends PluginCommand<GMToolsPlugin> {

    public GMCommand(GMToolsPlugin plugin) {
        super(plugin);
    }
}
