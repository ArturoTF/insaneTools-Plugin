package com.insaneItems.commands;

import com.insaneItems.items.ExplosiveTools;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExplosiveToolCommand implements CommandExecutor {

    private final String toolType;

    public ExplosiveToolCommand(String toolType) {
        this.toolType = toolType;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	// si lo pones en consola
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por jugadores.");
            return true;
        }
        
        Player player = (Player) sender;

        // Verifica si el jugador tiene permisos de operador
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "No tienes permiso para usar este comando.");
            return true;
        }

        ItemStack tool;

        switch (toolType.toLowerCase()) {
            case "pickaxe":
                tool = ExplosiveTools.createExplosivePickaxe();
                break;
            case "axe":
                tool = ExplosiveTools.createExplosiveAxe();
                break;
            case "shovel":
                tool = ExplosiveTools.createExplosiveShovel();
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Herramienta desconocida.");
                return true;
        }

        player.getInventory().addItem(tool);
        player.sendMessage(ChatColor.GREEN + "Has recibido una " + toolType + " explosiva.");
        return true;
    }

}
