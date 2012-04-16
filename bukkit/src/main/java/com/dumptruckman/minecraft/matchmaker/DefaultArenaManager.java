package com.dumptruckman.minecraft.matchmaker;

import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.api.ArenaManager;
import com.dumptruckman.minecraft.matchmaker.api.ArenaMap;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaConfig;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaRecord;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.YamlConfig;
import com.dumptruckman.minecraft.pluginbase.locale.Messager;
import com.dumptruckman.minecraft.pluginbase.util.Logging;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class DefaultArenaManager implements ArenaManager {

    private Arenas arenas = new Arenas();
    private MatchMakerPlugin matchMaker;

    private Map<String, String> selectedArenas = new HashMap<String, String>();
    
    public DefaultArenaManager(MatchMakerPlugin matchMaker) {
        this.matchMaker = matchMaker;
    }

    @Override
    public Arena getArena(String name) {
        for (Arena arena : getArenas()) {
            if (arena.getName().equalsIgnoreCase(name)) {
                return arena;
            }
        }
        return null;
    }

    public Arenas getArenas() {
        return arenas;
    }

    public Arena getIntersectingArena(Region region) {
        for (Arena arena : getArenas()) {
            if (!arena.getWorld().equals(region.getWorld().getName())) {
                continue;
            }
            if (region.getMaximumPoint().containedWithin(arena.getMinimumPoint(), arena.getMaximumPoint())
                || region.getMinimumPoint().containedWithin(arena.getMinimumPoint(), arena.getMaximumPoint())) {
                return arena;
            }
        }
        return null;
    }
    
    public Arena getArenaAt(String world, BlockVector vector) {
        for (Arena arena : getArenas()) {
            if (arena.contains(vector) && arena.getWorld().equals(world)) {
                return arena;
            }
        }
        return null;
    }

    public Arena newArena(String name, Region region) throws IllegalArgumentException, IOException {
        Messager messager = matchMaker.getMessager();
        if (getArena(name) != null) {
            throw new IllegalArgumentException(messager.getMessage(Language.CMD_CREATE_ARENA_EXISTING_NAME, name));
        }
        Arena existingArena = matchMaker.getArenaManager().getIntersectingArena(region);
        if (existingArena != null) {
            throw new IllegalArgumentException(messager.getMessage(Language.CMD_CREATE_ARENA_EXISTING_LOCATION, existingArena.getName()));
        }
        File arenaRecords = new File(matchMaker.getArenasFolder(), "records");
        Arena arena = Arenas.newArena(name, region,
                new YamlConfig<ArenaConfig>(matchMaker, false, new File(
                        matchMaker.getArenasFolder(), name + ".yml"), ArenaConfig.class),
                new YamlConfig<ArenaRecord>(matchMaker, false, new File(
                        arenaRecords, name + ".yml"), ArenaRecord.class));
        arenas.add(arena);
        return arena;
    }

    public void loadArenas() throws IOException {
        arenas.clear();
        File arenaFolder = matchMaker.getArenasFolder();
        if (!arenaFolder.exists()) {
            Logging.info("No arenas to load.");
            return;
        }
        File recordsFolder = new File(arenaFolder, "records");
        for (File file : arenaFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".yml");
            }
        })) {
            File recordFile = new File(recordsFolder, file.getName());
            Arena arena = Arenas.newArena(new YamlConfig<ArenaConfig>(matchMaker, false, file, ArenaConfig.class),
                    new YamlConfig<ArenaRecord>(matchMaker, false, recordFile, ArenaRecord.class));
            arenas.add(arena);
            Logging.finer("Loaded arena: " + arena);
        }
        Logging.info("Loaded " + arenas.size() + " arenas!");
    }
    
    public void loadMap(Arena arena, ArenaMap map, EditSession session) throws IllegalArgumentException, IllegalStateException, IOException, DataException, MaxChangedBlocksException {
        CuboidClipboard mapClipboard = map.getClipboard();
        if (!arena.isMapValid(mapClipboard)) {
            throw new IllegalArgumentException(matchMaker.getMessager().getMessage(Language.ARENA_LOAD_MAP, map.getName(), arena.getName()));
        }
        World world = Bukkit.getWorld(arena.getWorld());
        if (world == null) {
            throw new IllegalArgumentException(matchMaker.getMessager().getMessage(Language.ARENA_WORLD_UNLOADED, arena.getWorld(), arena.getName()));
        }
        mapClipboard.place(session, arena.getMinimumPoint(), false);
        session.flushQueue();
        arena.setMap(map);
    }

    private File getArenaFile(String name) {
        return new File(matchMaker.getArenasFolder(), name + ".yml");
    }

    @Override
    public boolean deleteArena(String name) throws IllegalArgumentException {
        File arenaFile = getArenaFile(name);
        if (!arenaFile.exists()) {
            throw new IllegalArgumentException(matchMaker.getMessager().getMessage(Language.NO_ARENA_NAMED, name));
        }
        removeFromArenas(name);
        return arenaFile.delete();
    }

    private void removeFromArenas(String name) {
        Iterator<Arena> it = arenas.iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(name)) {
                it.remove();
            }
        }
    }

    public void setSelectedArena(String player, Arena arena) {
        selectedArenas.put(player, arena.getName());
    }

    public Arena getSelectedArena(String player) {
        return getArena(selectedArenas.get(player));
    }
}
