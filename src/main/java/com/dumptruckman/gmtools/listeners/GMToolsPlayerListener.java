package com.dumptruckman.gmtools.listeners;

import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyPlayer;
import org.getchunky.chunky.permission.ChunkyAccessLevel;
import org.getchunky.chunky.permission.ChunkyPermissionChain;
import org.getchunky.chunky.permission.ChunkyPermissions;
import com.dumptruckman.gmtools.GMTools;
import com.dumptruckman.gmtools.configuration.Config;
import com.dumptruckman.gmtools.permissions.Perms;
import com.dumptruckman.gmtools.util.Logging;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * @author dumptruckman
 */
public class GMToolsPlayerListener extends PlayerListener {

    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getTypeId() == 51) fireBall(event);
        if (player.getItemInHand().getTypeId() == Config.BED_SPAWN_ITEM.getInteger()) bedSpawn(event);
    }

    public void fireBall(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!GMTools.getFireBallPlayers().contains(player)) return;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        final Vector direction = player.getEyeLocation().getDirection().multiply(2);
                player.getWorld().spawn(player.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), Fireball.class);
    }

    public void bedSpawn(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!Perms.BED_SPAWN.has(player)) return;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (GMTools.isChunky()) {
            ChunkyChunk cChunk = ChunkyManager.getChunk(player.getLocation());
            ChunkyPlayer cPlayer = ChunkyManager.getChunkyPlayer(player);
            ChunkyAccessLevel access = ChunkyPermissionChain.hasPerm(cChunk, cPlayer, ChunkyPermissions.Flags.ITEMUSE);
            if (access.causedDenial()) {
                player.sendMessage("You may not teleport out of chunks you cannot use items on!");
                return;
            }
        }

        Location loc = null;
        try {
            loc = player.getBedSpawnLocation();
        } catch (Exception ignore) {}
        if (loc == null) Bukkit.getWorld(Config.SPAWN_WORLD.getString()).getSpawnLocation();
        player.teleport(loc);
        
        ItemStack itemInHand = player.getItemInHand();
        int newAmount = itemInHand.getAmount() - 1;
        if (newAmount < 1)
            player.getInventory().clear(player.getInventory().getHeldItemSlot());
        else
            itemInHand.setAmount(newAmount);
    }



    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/spawn")) commandSpawn(event);
    }

    public void commandSpawn(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (GMTools.isChunky()) {
            ChunkyChunk cChunk = ChunkyManager.getChunk(player.getLocation());
            ChunkyPlayer cPlayer = ChunkyManager.getChunkyPlayer(player);
            if (ChunkyPermissionChain.hasPerm(cChunk, cPlayer, ChunkyPermissions.Flags.ITEMUSE).causedDenial()) {
                player.sendMessage("You may not teleport out of chunks you cannot use items on!");
                event.setCancelled(true);
            }
        }
    }
}
