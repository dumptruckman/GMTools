package com.dumptruckman.gmtools.commands;

import com.dumptruckman.gmtools.GMTools;
import com.dumptruckman.gmtools.permissions.Perms;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;

/**
 * @author dumptruckman
 */
public class FireballCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (!Perms.GM_FIREBALL.has(sender)) return true;

        Player player = (Player)sender;

        HashSet<Player> fireBallers = GMTools.getFireBallPlayers();
        if (fireBallers.contains(player)) {
            fireBallers.remove(player);
            player.sendMessage(ChatColor.GRAY + "You cool off.");
        } else {
            fireBallers.add(player);
            player.sendMessage(ChatColor.RED + "Yoga Flame.");
        }

        return true;
    }

}

