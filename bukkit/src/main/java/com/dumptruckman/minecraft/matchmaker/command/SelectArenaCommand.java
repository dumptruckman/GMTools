package com.dumptruckman.minecraft.matchmaker.command;

import com.dumptruckman.minecraft.matchmaker.MatchMakerPlugin;
import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.Perms;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SelectArenaCommand extends MMCommand {

    public SelectArenaCommand(MatchMakerPlugin plugin) {
        super(plugin);
        this.setName(messager.getMessage(Language.CMD_SELECT_ARENA_NAME));
        this.setCommandUsage(messager.getMessage(Language.CMD_SELECT_ARENA_USAGE, plugin.getCommandPrefixes().get(0)));
        this.setArgRange(0, 2);
        this.addPrefixedKey(" select arena");
        this.addPrefixedKey(" select");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " select arena " + ChatColor.GOLD + "Arena1");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " select " + ChatColor.GOLD + "Arena2 --we");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " select arena " + ChatColor.GOLD + "--we");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " select");
        this.setPermission(Perms.CMD_SELECT_ARENA.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        String name = null;
        Arena arena;
        boolean worldEdit = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--we")) {
                worldEdit = true;
            } else {
                name = arg;
            }
        }
        if (name != null) {
            arena = plugin.getArenaManager().getArena(name);
            if (arena == null) {
                messager.bad(Language.NO_ARENA_NAMED, sender, name);
                return;
            }
        } else {
            if (!(sender instanceof Player)) {
                messager.bad(Language.CMD_IN_GAME_ONLY, sender);
                return;
            }
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null, 100);
            if (block == null) {
                messager.bad(Language.MUST_TARGET_BLOCK, sender);
                return;
            }
            arena = plugin.getArenaManager().getArenaAt(player.getWorld().toString(), new BlockVector(block.getX(), block.getY(), block.getZ()));
            if (arena == null) {
                messager.bad(Language.NO_ARENA_AT_TARGET, sender);
                return;
            }
        }

        plugin.getArenaManager().setSelectedArena(sender.getName(), arena);
        if (worldEdit && sender instanceof Player) {
            Player player = (Player) sender;
            World world = Bukkit.getWorld(arena.getWorld());
            if (world == null) {
                messager.bad(Language.CMD_SELECT_ARENA_WORLD_UNLOADED, sender, arena.getWorld());
            } else {
                plugin.getWorldEdit().setSelection(player, new CuboidSelection(world, arena.getMinimumPoint(), arena.getMaximumPoint()));
                messager.good(Language.CMD_SELECT_ARENA_SUCCESS_WE, sender, arena.getName());
            }
        } else {
            messager.good(Language.CMD_SELECT_ARENA_SUCCESS, sender, arena.getName());
        }
    }
}
