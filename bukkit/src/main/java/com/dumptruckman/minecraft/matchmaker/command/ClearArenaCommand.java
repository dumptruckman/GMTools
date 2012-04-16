package com.dumptruckman.minecraft.matchmaker.command;

import com.dumptruckman.minecraft.matchmaker.MatchMakerPlugin;
import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.Perms;
import com.dumptruckman.minecraft.pluginbase.util.Logging;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sun.org.apache.bcel.internal.generic.LAND;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class ClearArenaCommand extends MMCommand {

    public ClearArenaCommand(MatchMakerPlugin plugin) {
        super(plugin);
        this.setName(messager.getMessage(Language.CMD_CLEAR_ARENA_NAME));
        this.setCommandUsage(messager.getMessage(Language.CMD_CLEAR_ARENA_USAGE, plugin.getCommandPrefixes().get(0)));
        this.setArgRange(0, 1);
        for (String prefix : plugin.getCommandPrefixes()) {
            this.addKey(prefix + " clear arena");
            this.addKey(prefix + " clear");
            this.addKey(prefix + " wipe");
            this.addKey(prefix + " wipe arena");
        }
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " clear arena " + ChatColor.GOLD + "Arena1");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " wipe");
        this.setPermission(Perms.CMD_CLEAR_ARENA.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)) {
            messager.bad(Language.CMD_IN_GAME_ONLY, sender);
            return;
        }
        Player player = (Player) sender;
        WorldEditPlugin worldEdit = plugin.getWorldEdit();
        Arena arena = null;
        if (!args.isEmpty()) {
            arena = plugin.getArenaManager().getArena(args.get(0));
        } else {
            arena = plugin.getArenaManager().getSelectedArena(player.getName());
        }
        if (arena == null) {
            messager.bad(Language.SELECT_ARENA_FIRST, player);
            return;
        }
        LocalWorld localWorld = null;
        for (LocalWorld world : worldEdit.getServerInterface().getWorlds()) {
            if (world.getName().equals(arena.getWorld())) {
                localWorld = world;
            }
        }
        if (localWorld == null) {
            Logging.warning("Could not retrieve WorldEdit world!");
            return;
        }

        try {
            LocalPlayer localPlayer = worldEdit.wrapPlayer(player);
            LocalSession lSession = worldEdit.getWorldEdit().getSession(localPlayer);
            RegionSelector regionSelector = lSession.getRegionSelector(localWorld);
            regionSelector.selectPrimary(arena.getMinimumPoint());
            regionSelector.selectSecondary(arena.getMaximumPoint());

            try {
                lSession.createEditSession(localPlayer).setBlocks(regionSelector.getRegion(), new BaseBlock(0, 0));
            } catch (IncompleteRegionException e) {
                e.printStackTrace();
            }
        } catch (MaxChangedBlocksException e) {
            messager.bad(Language.MAX_CHANGED_BLOCKS, player);
            return;
        }
        messager.good(Language.CMD_CLEAR_ARENA_SUCCESS, player, arena.getName());
    }
}
