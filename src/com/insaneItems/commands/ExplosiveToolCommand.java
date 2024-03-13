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
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack tool;

        switch (toolType.toLowerCase()) {
            case "pickaxe":
                tool = ExplosiveTools.createExplosivePickaxe();
                break;
            case "axe":
                tool = ExplosiveTools.createExplosiveAxe(); // Asegúrate de que este método devuelva un hacha.
                break;
            case "shovel":
                tool = ExplosiveTools.createExplosiveShovel(); // Asegúrate de que este método devuelva una pala.
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
