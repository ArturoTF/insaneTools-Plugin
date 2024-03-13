package com.insaneItems.commands;

import com.insaneItems.items.Treecapitator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TreecapitatorToolCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "No tienes permiso para usar este comando.");
            return true;
        }

        player.getInventory().addItem(Treecapitator.createTreecapitator());
        player.sendMessage(ChatColor.GREEN + "Has recibido el hacha Treecapitator.");
        return true;
    }
}
