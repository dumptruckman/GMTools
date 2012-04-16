package com.dumptruckman.minecraft.gmtools.api;

import com.dumptruckman.minecraft.pluginbase.plugin.PluginBase;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public interface GMTools extends PluginBase<GMConfig> {

    WorldEditPlugin getWorldEdit();
}
