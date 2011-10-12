package com.dumptruckman.gmtools.listeners;

import org.bukkit.block.Sign;
import org.getchunky.chunky.event.object.player.ChunkyPlayerBuildEvent;
import org.getchunky.chunky.event.object.player.ChunkyPlayerDestroyEvent;
import org.getchunky.chunky.event.object.player.ChunkyPlayerListener;

/**
 * @author dumptruckman
 */
public class ChunkyPlayerEvents extends ChunkyPlayerListener {

    public void onPlayerUnownedBuild(ChunkyPlayerBuildEvent event) {
        if (!event.isCancelled()) return;
        if (event.getBlock().getState() instanceof Sign) {
            Boolean allowed = event.getChunkyChunk().getData().optBoolean("BarterChunk");
            if (allowed == null) return;
            if (allowed) event.setCancelled(false);
        }
    }

    public void onPlayerUnownedBreak(ChunkyPlayerDestroyEvent event) {
        if (!event.isCancelled()) return;
        if (event.getBlock().getState() instanceof Sign) {
            Boolean allowed = event.getChunkyChunk().getData().optBoolean("BarterChunk");
            if (allowed == null) return;
            if (allowed) event.setCancelled(false);
        }
    }
}
