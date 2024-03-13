package com.insaneItems.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.insaneItems.commands.AutoPickupCommand;
import com.insaneItems.util.ToolUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.bukkit.ChatColor;

public class DeforestEnchant implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        Block block = event.getBlock();

        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasLore() && meta.getLore().contains(ChatColor.DARK_RED + "Deforest I") && ToolUtil.isApplicableForAxe(block.getType())) {
                event.setCancelled(true); // Cancelamos el evento para manejar nosotros la recolección de los bloques
                chopTree(block, item, player);
            }
        }
    }

    private void chopTree(Block startBlock, ItemStack tool, Player player) {
        BlockFace[] faces = {
            BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
            BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST
        };
        HashSet<Block> checkedBlocks = new HashSet<>();
        LinkedList<Block> toCheck = new LinkedList<>();

        toCheck.add(startBlock);

        while (!toCheck.isEmpty()) {
            Block block = toCheck.pop();

            if (!checkedBlocks.add(block)) {
                continue;
            }

            if (ToolUtil.isApplicableForAxe(block.getType())) {
                Collection<ItemStack> drops = block.getDrops(tool);
                if (AutoPickupCommand.isAutoPickupEnabled(player)) {
                    drops.forEach(drop -> {
                        HashMap<Integer, ItemStack> unadded = player.getInventory().addItem(drop);
                        unadded.values().forEach(remainingItem ->
                            player.getWorld().dropItemNaturally(block.getLocation(), remainingItem));
                    });
                } else {
                    drops.forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
                }
                block.setType(Material.AIR);

                // Añade los bloques adyacentes para verificar.
                for (BlockFace face : faces) {
                    Block relative = block.getRelative(face);
                    if (!checkedBlocks.contains(relative)) {
                        toCheck.add(relative);
                    }
                }
            }
        }

        // Verifica y elimina el encantamiento de Fortuna después de usar el hacha.
        if (tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            tool.removeEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
            player.sendMessage(ChatColor.YELLOW + "El encantamiento de Fortuna ha sido eliminado del Treecapitator.");
        }
    }


}
