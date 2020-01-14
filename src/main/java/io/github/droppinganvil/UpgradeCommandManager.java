package io.github.droppinganvil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpgradeCommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("seamlessupgrades.modify")) {
            commandSender.sendMessage(ChatColor.RED + "No permissions!");
            return true;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                Player target;
                SeamlessUpgrade upgrade;
                if (!SeamlessUpgrades.upgrades.containsKey(args[1])) {
                    return false;
                } else {
                    upgrade = SeamlessUpgrades.upgrades.get(args[1]);
                }
                target = Bukkit.getPlayer(args[2]);
                if (target == null) {
                    commandSender.sendMessage(ChatColor.RED + "Player not found.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    upgrade.addOne(target);
                } else {
                    upgrade.removeOne(target);
                }
                return true;
            }
        }
        return false;
    }
}
