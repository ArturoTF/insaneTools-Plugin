package com.insaneItems.util;

import org.bukkit.Material;

public class ToolUtil {

    public static boolean isApplicableForShovel(Material material) {
        return material == Material.SAND || material == Material.DIRT || material == Material.GRAVEL
               || material == Material.GRASS || material == Material.SOUL_SAND;
    }
    
    public static boolean isApplicableForAxe(Material material) {
        return material == Material.LOG || material == Material.LOG_2
               || material == Material.WOOD || material == Material.WOOD_DOUBLE_STEP
               || material == Material.WOOD_STEP || material == Material.WOOD_STAIRS
               || material == Material.FENCE || material == Material.FENCE_GATE
               || material == Material.WOOD_DOOR || material == Material.WOOD_PLATE
               || material == Material.TRAP_DOOR || material == Material.WORKBENCH
               || material == Material.BOOKSHELF || material == Material.CHEST
               || material == Material.TRAPPED_CHEST || material == Material.WOOD_BUTTON;
    }
}
