package com.dumptruckman.minecraft.matchmaker.api;

import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.dumptruckman.minecraft.matchmaker.api.config.RegionConfig;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;

import java.util.Set;

public interface MMRegion<V extends RegionConfig<V>> {

    Config<V> config();

    Vector getMinimumPoint();

    Vector getMaximumPoint();

    int getArea();

    int getWidth();

    int getHeight();

    int getLength();

    boolean contains(Vector vector);

    Set<Vector2D> getChunks();
}
