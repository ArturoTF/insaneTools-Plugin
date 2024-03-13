package com.insaneItems.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.insaneItems.util.ToolUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AutoPickupCommand implements CommandExecutor, Listener {
    private final static Set<UUID> autoPickupEnabled = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por jugadores.");
            return false;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();
        if (autoPickupEnabled.contains(playerId)) {
            autoPickupEnabled.remove(playerId);
            player.sendMessage(ChatColor.GREEN + "AutoPickup desactivado.");
        } else {
            autoPickupEnabled.add(playerId);
            player.sendMessage(ChatColor.GREEN + "AutoPickup activado.");
        }
        return true;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        Material toolType = item.getType();
        Block centerBlock = event.getBlock();

        // Verifica si la herramienta en uso tiene el lore "Explosivo I".
        if (hasExplosiveI(item)) {
            // Previene la caída natural de los ítems y el rompimiento del bloque si es necesario.
            event.setCancelled(true);

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Block relativeBlock = centerBlock.getRelative(x, y, z);
                        Material relativeBlockType = relativeBlock.getType();

                        boolean canBreak = false;
                        if (toolType == Material.DIAMOND_SPADE) {
                            canBreak = ToolUtil.isApplicableForShovel(relativeBlockType);
                        } else if (toolType == Material.DIAMOND_AXE) {
                            canBreak = ToolUtil.isApplicableForAxe(relativeBlockType);
                        } else {
                            // Asume que cualquier otra herramienta con "Explosivo I" puede romper cualquier bloque.
                            canBreak = true;
                        }

                        if (canBreak) {
                            // Maneja el rompimiento del bloque y los drops según si autopickup está activado o no.
                            handleBlockBreak(relativeBlock, item, player);
                        }
                    }
                }
            }
        }
    }

    private boolean hasExplosiveI(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            return meta.hasLore() && meta.getLore().contains(ChatColor.DARK_RED + "Explosivo I");
        }
        return false;
    }

    private void handleBlockBreak(Block block, ItemStack tool, Player player) {
        Collection<ItemStack> drops = block.getDrops(tool);
        
        if (isAutoPickupEnabled(player)) {
            // Si autopickup está activado, añade los drops directamente al inventario del jugador.
            for (ItemStack drop : drops) {
                player.getInventory().addItem(drop); // Considera verificar el espacio disponible.
            }
        } else {
            // Si autopickup no está activado, suelta los ítems en el mundo.
            drops.forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
        }
        block.setType(Material.AIR); // Elimina el bloque después de manejar los drops.
    }

    // Tus métodos isApplicableForShovel, isApplicableForAxe, y isAutoPickupEnabled...


  
 // Método para verificar si el auto recogido está activado para un jugador
    public static boolean isAutoPickupEnabled(Player player) {
        return autoPickupEnabled.contains(player.getUniqueId());
    }
    public void setAutoPickupEnabled(Player player, boolean enabled) {
        if (enabled) {
            autoPickupEnabled.add(player.getUniqueId());
        } else {
            autoPickupEnabled.remove(player.getUniqueId());
        }
    }
    
}
