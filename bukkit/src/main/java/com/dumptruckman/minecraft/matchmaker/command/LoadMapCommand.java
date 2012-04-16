package com.dumptruckman.minecraft.matchmaker.command;

import com.dumptruckman.minecraft.matchmaker.MatchMakerPlugin;
import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.api.ArenaMap;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.Perms;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class LoadMapCommand extends MMCommand {
    
    private final int BLOCKS_PER_CHUNK = 16;

    public LoadMapCommand(MatchMakerPlugin plugin) {
        super(plugin);
        this.setName(messager.getMessage(Language.CMD_LOAD_MAP_NAME));
        this.setCommandUsage(messager.getMessage(Language.CMD_LOAD_MAP_USAGE, plugin.getCommandPrefixes().get(0)));
        this.setArgRange(1, 2);
        for (String prefix : plugin.getCommandPrefixes()) {
            this.addKey(prefix + " load map");
        }
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " load map " + ChatColor.GOLD + "Map1");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " load map " + ChatColor.GOLD + "Map1 Arena1");
        this.setPermission(Perms.CMD_LOAD_MAP.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (args.size() == 1 && !(sender instanceof Player)) {
            messager.bad(Language.CMD_IN_GAME_ONLY, sender);
            return;
        }
        ArenaMap map = plugin.getMapManager().getMap(args.get(0));
        if (map == null) {
            messager.bad(Language.CMD_LOAD_MAP_NO_MAP, sender, args.get(0));
            return;
        }
        
        Arena arena = null;
        if (args.size() == 1) {
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null, Bukkit.getServer().getViewDistance() * BLOCKS_PER_CHUNK);
            if (block == null) {
                messager.bad(Language.MUST_TARGET_BLOCK, sender);
                return;
            }
            arena = plugin.getArenaManager().getArenaAt(player.getWorld().toString(), new BlockVector(block.getX(), block.getY(), block.getZ()));
        } else {
            arena = plugin.getArenaManager().getArena(args.get(1));
        }
        if (arena == null) {
            messager.bad(Language.CMD_LOAD_MAP_NO_ARENA, sender);
            return;
        }

        LocalPlayer player = plugin.getWorldEdit().wrapCommandSender(sender);

        try {
            plugin.getArenaManager().loadMap(arena, map,
                    plugin.getWorldEdit().getWorldEdit().getSession(player).createEditSession(player));
        } catch (IllegalArgumentException e) {
            messager.sendMessage(sender, e.getMessage());
            e.printStackTrace();
            return;
        } catch (IllegalStateException e) {
            messager.sendMessage(sender, e.getMessage());
            e.printStackTrace();
            return;
        } catch (DataException e) {
            messager.sendMessage(sender, e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            messager.sendMessage(sender, e.getMessage());
            e.printStackTrace();
            return;
        } catch (MaxChangedBlocksException e) {
            messager.sendMessage(sender, e.getMessage());
            e.printStackTrace();
            return;
        }

        messager.good(Language.CMD_LOAD_MAP_SUCCESS, sender, map.getName(), arena.getName());
    }
}
