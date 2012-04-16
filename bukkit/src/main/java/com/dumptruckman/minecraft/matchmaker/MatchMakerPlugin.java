package com.dumptruckman.minecraft.matchmaker;

import com.dumptruckman.minecraft.matchmaker.api.ArenaManager;
import com.dumptruckman.minecraft.matchmaker.api.MapManager;
import com.dumptruckman.minecraft.matchmaker.api.config.MMConfig;
import com.dumptruckman.minecraft.matchmaker.api.MatchMaker;
import com.dumptruckman.minecraft.matchmaker.command.ClearArenaCommand;
import com.dumptruckman.minecraft.matchmaker.command.CreateArenaCommand;
import com.dumptruckman.minecraft.matchmaker.command.CreateMapCommand;
import com.dumptruckman.minecraft.matchmaker.command.DeleteArenaCommand;
import com.dumptruckman.minecraft.matchmaker.command.DeleteMapCommand;
import com.dumptruckman.minecraft.matchmaker.command.LoadMapCommand;
import com.dumptruckman.minecraft.matchmaker.command.SelectArenaCommand;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.YamlPluginConfig;
import com.dumptruckman.minecraft.pluginbase.commandhandler.Command;
import com.dumptruckman.minecraft.pluginbase.plugin.AbstractBukkitPlugin;
import com.dumptruckman.minecraft.pluginbase.plugin.command.HelpCommand;
import com.dumptruckman.minecraft.pluginbase.util.Logging;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MatchMakerPlugin extends AbstractBukkitPlugin<MMConfig> implements MatchMaker<MMConfig> {
    
    private WorldEditPlugin worldEdit = null;
    private ArenaManager arenaManager = null;
    private MapManager mapManager = null;

    private final List<String> commandPrefixes = Arrays.asList("mm");
    
    private File arenaFolder = null;
    private File mapFolder = null;
    
    public void preEnable() {
        arenaFolder = new File(getDataFolder(), "arenas");
        mapFolder = new File(getDataFolder(), "maps");
        Language.init();
    }

    public void postEnable() {
        worldEdit = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
        registerCommands();
        try {
            getArenaManager().loadArenas();
        } catch (IOException e) {
            Logging.severe("Could not load arenas!");
            e.printStackTrace();
        }
    }

    public void preReload() {
        arenaManager = null;
        mapManager = null;
    }

    private void registerCommands() {
        getCommandHandler().registerCommand(new CreateArenaCommand(this));
        getCommandHandler().registerCommand(new CreateMapCommand(this));
        getCommandHandler().registerCommand(new LoadMapCommand(this));
        getCommandHandler().registerCommand(new DeleteArenaCommand(this));
        getCommandHandler().registerCommand(new DeleteMapCommand(this));
        getCommandHandler().registerCommand(new SelectArenaCommand(this));
        getCommandHandler().registerCommand(new ClearArenaCommand(this));
        for (Command c : getCommandHandler().getAllCommands()) {
            if (c instanceof HelpCommand) {
                for (String key : getCommandPrefixes()) {
                    c.addKey(key);
                }
            }
        }
    }

    @Override
    public List<String> getCommandPrefixes() {
        return commandPrefixes;
    }

    @Override
    protected MMConfig newConfigInstance() throws IOException {
        return new YamlPluginConfig(this, true, new File(getDataFolder(), "config.yml"), MMConfig.class, YamlPluginConfig.class);
    }
    
    public WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }

    @Override
    public ArenaManager getArenaManager() {
        if (arenaManager == null) {
            arenaManager = new DefaultArenaManager(this);
        }
        return arenaManager;
    }

    @Override
    public MapManager getMapManager() {
        if (mapManager == null) {
            mapManager = new DefaultMapManager(this);
        }
        return mapManager;
    }
    
    public File getArenasFolder() {
        if (!arenaFolder.exists()) {
            arenaFolder.mkdirs();
        }
        return arenaFolder;
    }

    public File getMapFolder() {
        if (!mapFolder.exists()) {
            mapFolder.mkdirs();
        }
        return mapFolder;
    }
}
