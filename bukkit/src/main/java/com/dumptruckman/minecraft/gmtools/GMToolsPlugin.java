package com.dumptruckman.minecraft.gmtools;

import com.dumptruckman.minecraft.gmtools.api.GMConfig;
import com.dumptruckman.minecraft.gmtools.api.GMTools;
import com.dumptruckman.minecraft.gmtools.command.TextCommand;
import com.dumptruckman.minecraft.gmtools.util.YamlPluginConfig;
import com.dumptruckman.minecraft.pluginbase.plugin.AbstractBukkitPlugin;
import com.dumptruckman.minecraft.pluginbase.plugin.command.HelpCommand;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GMToolsPlugin extends AbstractBukkitPlugin<GMConfig> implements GMTools {
    
    private WorldEditPlugin worldEdit = null;

    private final List<String> commandPrefixes = Arrays.asList("gm");
    
    public void preEnable() {
        HelpCommand.addStaticPrefixedKey("");
    }

    public void postEnable() {
        worldEdit = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
        registerCommands();
    }

    public void preReload() {
    }

    private void registerCommands() {
        getCommandHandler().registerCommand(new TextCommand(this));
    }

    @Override
    public List<String> getCommandPrefixes() {
        return commandPrefixes;
    }

    @Override
    protected GMConfig newConfigInstance() throws IOException {
        return new YamlPluginConfig(this, true, new File(getDataFolder(), "config.yml"), GMConfig.class, YamlPluginConfig.class);
    }
    
    public WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }
}
