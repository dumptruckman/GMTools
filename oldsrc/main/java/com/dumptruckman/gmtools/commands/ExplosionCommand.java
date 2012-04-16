package com.dumptruckman.gmtools.commands;

import com.dumptruckman.gmtools.GMTools;
import com.dumptruckman.gmtools.permissions.Perms;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

/**
 * @author dumptruckman
 */
public class ExplosionCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (!Perms.GM_FIREBALL.has(sender)) return true;

        Player player = (Player)sender;

        HashSet<Player> exploders = GMTools.getExplosionPlayers();
        if (exploders.contains(player)) {
            exploders.remove(player);
            player.sendMessage(ChatColor.GRAY + "Old spice...");
        } else {
            exploders.add(player);
            player.sendMessage(ChatColor.RED + "Building Kick!");
        }

        return true;
    }

}

