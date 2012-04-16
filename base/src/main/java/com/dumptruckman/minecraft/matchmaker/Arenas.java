package com.dumptruckman.minecraft.matchmaker;

import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaConfig;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaRecord;
import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.sk89q.worldedit.regions.Region;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Arenas implements Iterable<Arena> {

    private Set<Arena> arenas = new HashSet<Arena>();
    
    void add(Arena arena) {
        arenas.add(arena);
    }
    
    void clear() {
        arenas.clear();
    }
    
    int size() {
        return arenas.size();
    }

    @Override
    public Iterator<Arena> iterator() {
        return new ArenaIterator();
    }

    private class ArenaIterator implements Iterator<Arena> {

        Iterator<Arena> iterator = arenas.iterator();;
        /*
        ArenaIterator(Set<Arena> arenas) {
            iterator =
        }
        */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Arena next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            iterator.remove();
        }
    }
    
    static Arena newArena(String name, Region region, Config<ArenaConfig> config, Config<ArenaRecord> record) throws IllegalArgumentException {
        return new DefaultArena(name, region, config, record);
    }
    
    static Arena newArena(Config<ArenaConfig> config, Config<ArenaRecord> record) {
        return new DefaultArena(config, record);
    }
}
