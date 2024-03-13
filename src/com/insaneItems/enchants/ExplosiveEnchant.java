package com.insaneItems.enchants;

import java.util.Collection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.insaneItems.commands.AutoPickupCommand;
import com.insaneItems.util.ToolUtil;

public class ExplosiveEnchant implements Listener {
	
    


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInHand();
        ItemMeta meta = item.getItemMeta();

        if (item != null && meta != null && meta.hasLore() && meta.getLore().contains(ChatColor.DARK_RED + "Explosivo I")) {
            // Verifica si la herramienta tiene el encantamiento de Fortuna y lo elimina.
            if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                meta.removeEnchant(Enchantment.LOOT_BONUS_BLOCKS);
                item.setItemMeta(meta);
                event.getPlayer().sendMessage(ChatColor.YELLOW + "El encantamiento de Fortuna ha sido eliminado.");
            }

            // Cancela el evento para evitar la caída de items naturalmente.
            event.setCancelled(true);
            Block centerBlock = event.getBlock();

            // Procesa todos los bloques en un cubo de 3x3x3 alrededor del bloque central.
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Block relativeBlock = centerBlock.getRelative(x, y, z);
                        processBlock(relativeBlock, item, event.getPlayer()); // Ajuste aquí
                    }
                }
            }
        }
    }

    private void processBlock(Block block, ItemStack tool, Player player) {
        Material toolType = tool.getType();
        Material blockType = block.getType();

        boolean canBreak = false;

        if (toolType == Material.DIAMOND_SPADE) {
            canBreak = ToolUtil.isApplicableForShovel(blockType); // Pala solo para ciertos tipos de bloques
        } else if (toolType == Material.DIAMOND_AXE) {
            canBreak = ToolUtil.isApplicableForAxe(blockType); // Hacha solo para bloques de madera
        } else {
            // Aquí asumimos que el pico explosivo puede romper cualquier bloque, para cambiar esto crear un metodo isApplicableForPickaxe
            canBreak = true;
        }

        if (canBreak) {
            Collection<ItemStack> drops = block.getDrops(tool);

            if (AutoPickupCommand.isAutoPickupEnabled(player)) {
                // Recoge los ítems automáticamente si autopickup está activado
                for (ItemStack drop : drops) {
                    player.getInventory().addItem(drop); // Asegúrate de manejar el inventario lleno
                }
            } else {
                // Suelta los ítems naturalmente si autopickup no está activado
                drops.forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
            }

            block.setType(Material.AIR); // Simula que el bloque ha sido roto.
        }
    }
    
   


}
